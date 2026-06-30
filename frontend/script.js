// Authentication Check
const currentUser = JSON.parse(localStorage.getItem('currentUser'));
if (!currentUser) {
    window.location.href = 'auth.html';
}

function logout() {
    localStorage.removeItem('currentUser');
    window.location.href = 'auth.html';
}

// Data State
let accounts = {
    '1': { balance: 2000000, currentExpense: 0, budgetLimit: 500000, transactions: [], historyStack: [], redoStack: [] },
    '2': { balance: 15000000, currentExpense: 0, budgetLimit: 1000000, transactions: [], historyStack: [], redoStack: [] }
};
let currentAccountId = '1';
let isUSD = false;
const USD_RATE = 15500;

// Default Goals
let goalState = {
    title: "Beli Laptop",
    target: 10000000
};

// Load from LocalStorage if exists
const savedData = localStorage.getItem('smartexpense_data');
if (savedData) {
    const parsed = JSON.parse(savedData);
    accounts = parsed.accounts || parsed; // Support old format
    if (parsed.goalState) goalState = parsed.goalState;
}

// Helper to save state
function saveState() {
    localStorage.setItem('smartexpense_data', JSON.stringify({ accounts, goalState }));
}

function getActiveAccount() {
    return accounts[currentAccountId] || accounts['1'];
}

// Profile/Goal Management
function editGoal() {
    const modal = document.getElementById('goal-modal');
    if(modal) {
        document.getElementById('modal-goal-title').value = goalState.title;
        document.getElementById('modal-goal-target').value = goalState.target;
        modal.style.display = 'flex';
    }
}

function editBudget() {
    const modal = document.getElementById('budget-modal');
    if(modal) {
        const account = getActiveAccount();
        document.getElementById('modal-budget-limit').value = account.budgetLimit;
        modal.style.display = 'flex';
    }
}

// Modal Event Listeners
document.addEventListener('DOMContentLoaded', () => {
    const btnCloseModal = document.getElementById('btn-close-modal');
    const btnSaveGoal = document.getElementById('btn-save-goal');
    const modal = document.getElementById('goal-modal');

    if(btnCloseModal) {
        btnCloseModal.addEventListener('click', () => modal.style.display = 'none');
    }

    if(btnSaveGoal) {
        btnSaveGoal.addEventListener('click', () => {
            const newTitle = document.getElementById('modal-goal-title').value;
            const newTarget = document.getElementById('modal-goal-target').value;
            if(newTitle && newTarget && !isNaN(newTarget)) {
                goalState.title = newTitle;
                goalState.target = parseFloat(newTarget);
                saveState();
                updateGoalTracker();
                modal.style.display = 'none';
            }
        });
    }

    const btnCloseBudget = document.getElementById('btn-close-budget-modal');
    const btnSaveBudget = document.getElementById('btn-save-budget');
    const budgetModal = document.getElementById('budget-modal');

    if(btnCloseBudget) {
        btnCloseBudget.addEventListener('click', () => budgetModal.style.display = 'none');
    }

    if(btnSaveBudget) {
        btnSaveBudget.addEventListener('click', () => {
            const newLimit = document.getElementById('modal-budget-limit').value;
            if(newLimit && !isNaN(newLimit)) {
                const account = getActiveAccount();
                account.budgetLimit = parseFloat(newLimit);
                saveState();
                updateUI(); // This will refresh the budget display and alert box
                budgetModal.style.display = 'none';
            }
        });
    }
});

// DOM Elements
const form = document.getElementById('transaction-form');
const tbody = document.getElementById('transaction-tbody');
const balanceDisplay = document.getElementById('balance-display');
const alertArea = document.getElementById('alert-area');
const alertMessage = document.getElementById('alert-message');
const btnCurrency = document.getElementById('btn-currency');
const btnUndo = document.getElementById('btn-undo');
const btnRedo = document.getElementById('btn-redo');
const btnExportCSV = document.getElementById('btn-export-csv');
const btnExportJSON = document.getElementById('btn-export-json');
const btnThemeToggle = document.getElementById('btn-theme-toggle');
const searchInput = document.getElementById('search-input');
const filterType = document.getElementById('filter-type');
const accountSelector = document.getElementById('account-selector');
const goalCurrent = document.getElementById('goal-current');
const goalTarget = document.getElementById('goal-target');
const goalProgress = document.getElementById('goal-progress');
const btnBackup = document.getElementById('btn-backup');
const restoreFile = document.getElementById('restore-file');

// Initialize Theme
const currentTheme = localStorage.getItem('theme') || 'light';
if (currentTheme === 'dark') {
    document.documentElement.setAttribute('data-theme', 'dark');
    if (btnThemeToggle) btnThemeToggle.innerHTML = '<i class="fa-solid fa-sun"></i>';
}

// Initialize UI
if (currentUser) {
    const userNameElement = document.getElementById('user-name-display');
    const userAvatarElement = document.getElementById('user-avatar');
    if (userNameElement) userNameElement.textContent = `Halo, ${currentUser.name}`;
    if (userAvatarElement) userAvatarElement.src = `https://ui-avatars.com/api/?name=${encodeURIComponent(currentUser.name)}&background=D4AF37&color=fff`;
}

// Event Listeners
if (btnThemeToggle) {
    btnThemeToggle.addEventListener('click', () => {
        let theme = document.documentElement.getAttribute('data-theme');
        if (theme === 'dark') {
            document.documentElement.removeAttribute('data-theme');
            localStorage.setItem('theme', 'light');
            btnThemeToggle.innerHTML = '<i class="fa-solid fa-moon"></i>';
        } else {
            document.documentElement.setAttribute('data-theme', 'dark');
            localStorage.setItem('theme', 'dark');
            btnThemeToggle.innerHTML = '<i class="fa-solid fa-sun"></i>';
        }
    });
}

if (searchInput) searchInput.addEventListener('input', renderTable);
if (filterType) filterType.addEventListener('change', renderTable);
if (accountSelector) {
    accountSelector.addEventListener('change', (e) => {
        currentAccountId = e.target.value;
        updateUI();
    });
}

if(form) {
    form.addEventListener('submit', function(e) {
        e.preventDefault(); 
        handleTransactionSubmission();
    });
}

function handleTransactionSubmission() {
    const type = document.getElementById('type').value;
    const amount = parseFloat(document.getElementById('amount').value);
    const note = document.getElementById('note').value;
    const isRecurring = document.getElementById('is-recurring') ? document.getElementById('is-recurring').checked : false;
    const attachmentInput = document.getElementById('attachment');
    let attachmentPath = null;

    if (attachmentInput && attachmentInput.files.length > 0) {
        attachmentPath = attachmentInput.files[0].name; 
    }
    
    let finalNote = note;
    if (isRecurring) finalNote += " (Berulang)";
    if (attachmentPath) finalNote += ` [Lampiran: ${attachmentPath}]`;

    const transaction = { id: Date.now(), type, amount, note: finalNote, date: new Date().toISOString() };
    
    const account = getActiveAccount();
    executeTransaction(account, transaction);
    account.historyStack.push(transaction);
    account.redoStack = [];
    
    saveState();
    if(form) form.reset();
    updateUI();
}

if(btnCurrency) {
    btnCurrency.addEventListener('click', () => {
        isUSD = !isUSD;
        btnCurrency.innerHTML = isUSD ? 'USD <i class="fa-solid fa-arrows-rotate"></i>' : 'IDR <i class="fa-solid fa-arrows-rotate"></i>';
        updateUI();
    });
}

if(btnUndo) {
    btnUndo.addEventListener('click', () => {
        const account = getActiveAccount();
        if (account.historyStack.length > 0) {
            const lastTx = account.historyStack.pop();
            undoTransaction(account, lastTx);
            account.redoStack.push(lastTx);
            account.transactions = account.transactions.filter(t => t.id !== lastTx.id);
            saveState();
            updateUI();
        }
    });
}

if(btnRedo) {
    btnRedo.addEventListener('click', () => {
        const account = getActiveAccount();
        if (account.redoStack.length > 0) {
            const txToRedo = account.redoStack.pop();
            executeTransaction(account, txToRedo);
            account.historyStack.push(txToRedo);
            saveState();
            updateUI();
        }
    });
}

// Backup & Restore
if (btnBackup) {
    btnBackup.addEventListener('click', () => {
        const jsonStr = JSON.stringify({ accounts, goalState }, null, 2);
        const dataUri = "data:application/json;charset=utf-8," + encodeURIComponent(jsonStr);
        downloadFile(dataUri, "smartexpense_backup.json");
    });
}

if (restoreFile) {
    restoreFile.addEventListener('change', (e) => {
        const file = e.target.files[0];
        if (!file) return;
        const reader = new FileReader();
        reader.onload = function(e) {
            try {
                const loadedData = JSON.parse(e.target.result);
                accounts = loadedData.accounts || loadedData;
                if(loadedData.goalState) goalState = loadedData.goalState;
                saveState();
                updateUI();
                alert('Data berhasil di-restore!');
            } catch (err) {
                alert('Gagal membaca file backup.');
            }
        };
        reader.readAsText(file);
    });
}

if (btnExportCSV) {
    btnExportCSV.addEventListener('click', () => {
        let csvContent = "data:text/csv;charset=utf-8,Tanggal,Jenis,Keterangan,Nominal\n";
        getActiveAccount().transactions.forEach(t => {
            csvContent += `${t.date.split('T')[0]},${t.type},${t.note},${t.amount}\n`;
        });
        downloadFile(encodeURI(csvContent), "transactions_account_" + currentAccountId + ".csv");
    });
}

if (btnExportJSON) {
    btnExportJSON.addEventListener('click', () => {
        const jsonStr = JSON.stringify(getActiveAccount().transactions, null, 2);
        const dataUri = "data:application/json;charset=utf-8," + encodeURIComponent(jsonStr);
        downloadFile(dataUri, "transactions_account_" + currentAccountId + ".json");
    });
}

// Core Logic
function executeTransaction(account, tx) {
    if (tx.type === 'income') {
        account.balance += tx.amount;
    } else {
        account.balance -= tx.amount;
        account.currentExpense += tx.amount;
    }
    if (!account.transactions.find(t => t.id === tx.id)) {
        account.transactions.unshift(tx);
    } else {
        account.transactions = account.transactions.filter(t => t.id !== tx.id);
        account.transactions.unshift(tx);
    }
}

function undoTransaction(account, tx) {
    if (tx.type === 'income') {
        account.balance -= tx.amount;
    } else {
        account.balance += tx.amount;
        account.currentExpense -= tx.amount;
    }
}

function checkBudgetObserver(account) {
    if (!alertArea) return;
    if (account.currentExpense >= account.budgetLimit) {
        alertArea.style.display = 'block';
        alertMessage.innerHTML = `<strong>BAHAYA!</strong> Pengeluaran (Rp ${account.currentExpense}) telah melebihi batas anggaran (Rp ${account.budgetLimit})!`;
    } else if (account.currentExpense >= account.budgetLimit * 0.8) {
        alertArea.style.display = 'block';
        alertMessage.innerHTML = `<strong>PERINGATAN!</strong> Pengeluaran (Rp ${account.currentExpense}) sudah mencapai 80% dari batas anggaran.`;
    } else {
        alertArea.style.display = 'none';
    }
}

function formatCurrency(amount) {
    return new Intl.NumberFormat('id-ID', { style: 'currency', currency: 'IDR' }).format(amount);
}

// UI Updates
function updateUI() {
    const account = getActiveAccount();
    updateBalanceDisplay(account);
    updateGoalTracker();
    
    // Update budget display
    const budgetDisplay = document.getElementById('budget-limit-display');
    if (budgetDisplay) {
        budgetDisplay.textContent = formatCurrency(account.budgetLimit);
    }
    
    checkBudgetObserver(account);
    renderTable();
    if(btnUndo) btnUndo.disabled = account.historyStack.length === 0;
    if(btnRedo) btnRedo.disabled = account.redoStack.length === 0;
}

function updateBalanceDisplay(account) {
    if (!balanceDisplay) return;
    if (isUSD) {
        const usdBalance = account.balance / USD_RATE;
        balanceDisplay.textContent = new Intl.NumberFormat('en-US', { style: 'currency', currency: 'USD' }).format(usdBalance);
    } else {
        balanceDisplay.textContent = formatCurrency(account.balance);
    }
}

function updateGoalTracker() {
    if (!goalCurrent) return;
    const goalTitleDisplay = document.getElementById('goal-title-display');
    if (goalTitleDisplay) goalTitleDisplay.textContent = goalState.title;
    
    // We assume the total of all account balances contributes to the goal
    const totalBalance = Object.values(accounts).reduce((sum, acc) => sum + acc.balance, 0);
    goalCurrent.textContent = formatCurrency(totalBalance);
    if(goalTarget) goalTarget.textContent = formatCurrency(goalState.target);
    const progressPercent = Math.min((totalBalance / goalState.target) * 100, 100);
    if(goalProgress) goalProgress.style.width = `${progressPercent}%`;
}

function renderTable() {
    if(!tbody) return;
    tbody.innerHTML = '';
    const account = getActiveAccount();
    const searchTerm = searchInput ? searchInput.value.toLowerCase() : '';
    const filterVal = filterType ? filterType.value : 'all';

    const filteredTransactions = account.transactions.filter(tx => {
        const matchesSearch = tx.note.toLowerCase().includes(searchTerm) || tx.amount.toString().includes(searchTerm);
        const matchesType = filterVal === 'all' || tx.type === filterVal;
        return matchesSearch && matchesType;
    });

    filteredTransactions.forEach(tx => {
        let displayAmount = tx.type === 'expense' ? `- ${formatCurrency(tx.amount)}` : `+ ${formatCurrency(tx.amount)}`;
        let textClass = tx.type === 'expense' ? 'text-expense' : 'text-income';
        let badgeClass = tx.type === 'expense' ? 'badge-expense' : 'badge-income';
        let categoryText = tx.type === 'expense' ? 'Pengeluaran' : 'Pemasukan';
        
        const tr = document.createElement('tr');
        tr.innerHTML = `
            <td><span class="badge ${badgeClass}">${tx.date.split('T')[0]}</span></td>
            <td>
                <strong>${tx.note}</strong><br>
                <small class="text-muted">${categoryText}</small>
            </td>
            <td class="text-right ${textClass}">${displayAmount}</td>
        `;
        tbody.appendChild(tr);
    });
}

function downloadFile(uri, filename) {
    const link = document.createElement("a");
    link.setAttribute("href", uri);
    link.setAttribute("download", filename);
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
}

// Initial Call
updateUI();
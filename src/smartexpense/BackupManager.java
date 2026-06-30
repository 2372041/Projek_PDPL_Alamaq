package smartexpense;

import java.util.Date;

public class BackupManager {
    private static BackupManager instance;
    private String filePath;
    private Date lastBackupDate;

    private BackupManager() {
        System.out.println("[Singleton] BackupManager diinisialisasi...");
    }

    public static BackupManager getInstance() {
        if (instance == null) {
            instance = new BackupManager();
        }
        return instance;
    }

    public void backupData(String filepath) {
        this.filePath = filepath;
        this.lastBackupDate = new Date();
        System.out.println("[Backup] Mem-backup data ke " + filepath);
    }

    public void restoreData(String filepath) {
        System.out.println("[Backup] Me-restore data dari " + filepath);
    }

    public String getLastBackupInfo() {
        return "Last Backup: " + (lastBackupDate != null ? lastBackupDate.toString() : "None");
    }

    public void scheduleAutoBackup() {
        System.out.println("[Backup] Auto-backup dijadwalkan.");
    }
}

# Mencoba 3 Kasus dalam Github
# 2372041 - Axel Hizkia Mapandin
def print_array(arr):
    print(*(arr))

# SHELL SORT
def shell_sort(arr):
    n = len(arr)
    gap = n // 2
    
    while gap > 0:
        for i in range(gap, n):
            j = i
            while j >= gap:
                if arr[j - gap] < arr[j]:
                    arr[j], arr[j - gap] = arr[j - gap], arr[j]
                    print_array(arr)
                    j -= gap
                else:
                    break
        gap //= 2

# -- Skenario satu testttttttttttttttttt --

# PROGRAM UTAMA
def main():
    try:
        n_input = input("Nilai N :")
        n = int(n_input)
    except ValueError:
        print("Input N harus angka.")
        return

    arr = []
    for _ in range(n):
        val = int(input("Nilai x :"))
        arr.append(val)

    print("Kondisi Awal Array:")
    print_array(arr)

    print("Proses Shellsort:")
    shell_sort(arr)

if __name__ == "__main__":
    main()
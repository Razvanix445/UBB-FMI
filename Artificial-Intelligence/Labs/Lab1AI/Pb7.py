# 7. Să se determine al k-lea cel mai mare element al unui șir de numere cu n elemente (k < n).
# De ex. al 2-lea cel mai mare element din șirul [7,4,6,3,9,1] este 7.

def pb7():
    # Initializam sirul si apoi il sortam descrescator
    sir = [7, 4, 6, 3, 9, 1]
    sir = sorted(sir, reverse=True)
    k = 2

    # Parcurgem sirul pana ajungem la k inegalitati de comparatie, apoi afisam elementul la care s-a ajuns
    for i in range(len(sir)):
        if sir[i] != sir[i + 1]:
            k = k - 1
        if (k == 0):
            return print(sir[i])


def pb7CuBot():
    # Exemplu de utilizare
    sir = [7, 4, 6, 3, 9, 1]
    k = 2

    # S-a folosit metoda asa-numita "selection algorithm" sau "quickselect".
    print(kth_largest_element(sir, k))


def partition(arr, low, high):
    pivot = arr[high]
    i = low - 1
    for j in range(low, high):
        if arr[j] >= pivot:
            i += 1
            arr[i], arr[j] = arr[j], arr[i]
    arr[i + 1], arr[high] = arr[high], arr[i + 1]
    return i + 1


def kth_largest_element(arr, k):
    low = 0
    high = len(arr) - 1

    while low <= high:
        pivot_index = partition(arr, low, high)
        if pivot_index == k - 1:
            return arr[pivot_index]
        elif pivot_index < k - 1:
            low = pivot_index + 1
        else:
            high = pivot_index - 1
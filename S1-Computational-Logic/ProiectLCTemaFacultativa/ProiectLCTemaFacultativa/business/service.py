class Service:

    def __init__(self):
        pass

    def conversie_substitutie_baza_intermediara_impartiri_succesive(self, numar, baza_initiala, baza_finala):
        '''
        Se realizeaza conversia dintr-o baza intr-o alta oricare baza prin substitutie din baza initiala in baza 10,
        baza intermediara, iar, din baza 10, in baza finala, prin metoda impartirilor succesive. Se foloseste baza 10
        ca baza intermediara.
        :param numar: (string) numarul care se va converti in alta baza
        :param baza_initiala: (int) baza in care se afla numarul citit
        :param baza_finala: (int) baza in care se va converti numarul citit
        :return: se returneaza valoarea numarului 'numar' in baza 'baza_finala'
        '''
        # Definim un dicționar pentru a asocia fiecărei litere din baza 16 valoarea corespunzătoare în baza 10
        valori_hexa = {
            'A': 10,
            'B': 11,
            'C': 12,
            'D': 13,
            'E': 14,
            'F': 15
        }

        # Definim o funcție pentru a obține valoarea corespunzătoare cifrei/literei pentru orice bază dată
        def get_valoare(cifra, baza):
            if cifra.isdigit():
                return int(cifra)
            else:
                return valori_hexa[cifra.upper()]

        # Convertim numărul din baza inițială în baza 10 prin metoda substitutiei pentru a folosi o baza intermediara (baza 10)
        numar10 = 0
        for i in range(len(numar)):
            cifra = numar[i]
            valoare_cifra = get_valoare(cifra, baza_initiala)
            numar10 += valoare_cifra * (baza_initiala ** (len(numar) - i - 1))

        # Convertim numărul din baza 10 în baza dorită prin metoda impartirilor succesive
        cifre = []
        while numar10 > 0:
            rest = numar10 % baza_finala
            if rest < 10:
                cifre.append(str(rest))
            else:
                litere = ['A', 'B', 'C', 'D', 'E', 'F']
                cifre.append(litere[rest - 10])
            numar10 //= baza_finala

        # Returnăm numărul convertit în baza dorită
        if cifre:
            return ''.join(cifre[::-1])
        else:
            return '0'

    def conversie_rapida(self, numar, baza_initiala, baza_finala):
        '''
        Se realizeaza conversia rapida intre bazele 2, 4, 8, 16, utilizand tabelele pentru conversii rapide.
        :param numar: (string) numarul initial care va fi convertit in alta baza
        :param baza_initiala: (int) baza in care se afla la inceput numarul 'numar'
        :param baza_finala: (int) baza in care se va face conversia
        :return: se returneaza valoarea numarului 'numar' aflat in baza 'baza_finala'
        '''
        baza_2_to_4 = {'00': '0', '01': '1', '10': '2', '11': '3'}
        baza_2_to_8 = {'000': '0', '001': '1', '010': '2', '011': '3', '100': '4', '101': '5', '110': '6', '111': '7'}
        baza_2_to_16 = {'0000': '0', '0001': '1', '0010': '2', '0011': '3', '0100': '4', '0101': '5', '0110': '6', '0111': '7', '1000': '8', '1001': '9', '1010': 'A', '1011': 'B', '1100': 'C', '1101': 'D', '1110': 'E', '1111': 'F'}
        baza_4_to_2 = {'0': '00', '1': '01', '2': '10', '3': '11'}
        baza_8_to_2 = {'0': '000', '1': '001', '2': '010', '3': '011', '4': '100', '5': '101', '6': '110', '7': '111'}
        baza_16_to_2 = {'0': '0000', '1': '0001', '2': '0010', '3': '0011', '4': '0100', '5': '0101', '6': '0110', '7': '0111', '8': '1000', '9': '1001', 'A': '1010', 'B': '1011', 'C': '1100', 'D': '1101', 'E': '1110', 'F': '1111'}
        if baza_initiala == 2 and baza_finala == 16:
            numar = numar.zfill((len(numar) + 3) // 4 * 4)
            groups = [numar[i:i + 4] for i in range(0, len(numar), 4)]
            cifre_hexa = [baza_2_to_16[group] for group in groups]
            numar_final = ''.join(cifre_hexa)
            return numar_final
        elif baza_initiala == 2 and baza_finala == 8:
            numar = numar.zfill((len(numar) + 2) // 3 * 3)
            groups = [numar[i:i + 3] for i in range(0, len(numar), 3)]
            cifre_octal = [baza_2_to_8[group] for group in groups]
            numar_final = ''.join(cifre_octal)
            return numar_final
        elif baza_initiala == 2 and baza_finala == 4:
            numar = numar.zfill((len(numar) + 1) // 2 * 2)
            groups = [numar[i:i + 2] for i in range(0, len(numar), 2)]
            cifre_cuat = [baza_2_to_4[group] for group in groups]
            numar_final = ''.join(cifre_cuat)
            return numar_final

        elif baza_initiala == 16 and baza_finala == 2:
            cifre_hexa = [baza_16_to_2[digit] for digit in numar.upper()]
            numar_binar = ''.join(cifre_hexa)
            return numar_binar
        elif baza_initiala == 8 and baza_finala == 2:
            cifre_octal = [baza_8_to_2[digit] for digit in numar.upper()]
            numar_binar = ''.join(cifre_octal)
            return numar_binar
        elif baza_initiala == 4 and baza_finala == 2:
            cifre_cuat = [baza_4_to_2[digit] for digit in numar.upper()]
            numar_binar = ''.join(cifre_cuat)
            return numar_binar

        elif baza_initiala == 4 and baza_finala == 8:
            cifre_cuat = [baza_4_to_2[digit] for digit in numar.upper()]
            numar_binar = ''.join(cifre_cuat)
            numar_binar = numar_binar.zfill((len(numar_binar) + 2) // 3 * 3)
            groups = [numar_binar[i:i + 3] for i in range(0, len(numar_binar), 3)]
            cifre_octal = [baza_2_to_8[group] for group in groups]
            numar_final = ''.join(cifre_octal)
            return numar_final
        elif baza_initiala == 4 and baza_finala == 16:
            cifre_cuat = [baza_4_to_2[digit] for digit in numar.upper()]
            numar_binar = ''.join(cifre_cuat)
            numar_binar = numar_binar.zfill((len(numar_binar) + 3) // 4 * 4)
            groups = [numar_binar[i:i + 4] for i in range(0, len(numar_binar), 4)]
            cifre_hexa = [baza_2_to_16[group] for group in groups]
            numar_final = ''.join(cifre_hexa)
            return numar_final
        elif baza_initiala == 8 and baza_finala == 4:
            cifre_octal = [baza_8_to_2[digit] for digit in numar.upper()]
            numar_binar = ''.join(cifre_octal)
            numar_binar = numar_binar.zfill((len(numar_binar) + 1) // 2 * 2)
            groups = [numar_binar[i:i + 2] for i in range(0, len(numar_binar), 2)]
            cifre_cuat = [baza_2_to_4[group] for group in groups]
            numar_final = ''.join(cifre_cuat)
            return numar_final
        elif baza_initiala == 8 and baza_finala == 16:
            cifre_octal = [baza_8_to_2[digit] for digit in numar.upper()]
            numar_binar = ''.join(cifre_octal)
            numar_binar = numar_binar.zfill((len(numar_binar) + 3) // 4 * 4)
            groups = [numar_binar[i:i + 4] for i in range(0, len(numar_binar), 4)]
            cifre_hexa = [baza_2_to_16[group] for group in groups]
            numar_final = ''.join(cifre_hexa)
            return numar_final
        elif baza_initiala == 16 and baza_finala == 4:
            cifre_hexa = [baza_16_to_2[digit] for digit in numar.upper()]
            numar_binar = ''.join(cifre_hexa)
            numar_binar = numar_binar.zfill((len(numar_binar) + 1) // 2 * 2)
            groups = [numar_binar[i:i + 2] for i in range(0, len(numar_binar), 2)]
            cifre_cuat = [baza_2_to_4[group] for group in groups]
            numar_final = ''.join(cifre_cuat)
            return numar_final
        elif baza_initiala == 16 and baza_finala == 8:
            cifre_hexa = [baza_16_to_2[digit] for digit in numar.upper()]
            numar_binar = ''.join(cifre_hexa)
            numar_binar = numar_binar.zfill((len(numar_binar) + 2) // 3 * 3)
            groups = [numar_binar[i:i + 3] for i in range(0, len(numar_binar), 3)]
            cifre_octal = [baza_2_to_8[group] for group in groups]
            numar_final = ''.join(cifre_octal)
            return numar_final
        else:
            raise ValueError('Bazele nu sunt valide!\n')

    def adunare(self, numar1, baza1, numar2, baza2, baza):
        '''
        Se realizeaza adunarea a doua numere intr-o baza data. Primul numar este intr-o baza data ca parametru, iar al
        doilea la fel. Bazele pot fi diferite si se va oferi si o alta baza in care se va calcula rezultatul.
        :param numar1: (string) primul numar pentru adunare
        :param baza1: (int) baza primului numar
        :param numar2: (string) al doilea numar pentru adunare
        :param baza2: (int) baza celui de-al doilea numar
        :param baza: (int) baza in care se vor aduna numerele
        :return: se va returna suma celor doua numere intr-o baza data
        '''
        numar1 = self.conversie_substitutie_baza_intermediara_impartiri_succesive(numar1, baza1, baza)
        numar2 = self.conversie_substitutie_baza_intermediara_impartiri_succesive(numar2, baza2, baza)

        # Definim un dictionar pentru toate cifrele pana la baza 16
        cifre = {'0': 0, '1': 1, '2': 2, '3': 3, '4': 4, '5': 5, '6': 6, '7': 7, '8': 8, '9': 9, 'A': 10, 'B': 11, 'C': 12, 'D': 13, 'E': 14, 'F': 15}

        # Definim un dictionar pentru valorile zecimale pentru cifrele lor
        inv_cifre = {v: k for k, v in cifre.items()}

        # Inversam numerele pentru a simplifica adunarea
        numar1 = numar1[::-1]
        numar2 = numar2[::-1]

        # Facem numerele de aceeasi dimensiune prin adaugarea zero-urilor necesare la inceputul numarului respectiv
        max_len = max(len(numar1), len(numar2))
        numar1 = numar1.ljust(max_len, '0')
        numar2 = numar2.ljust(max_len, '0')

        # Initializam rezultatul si carry, unde vom memora transportul
        rezultat = ''
        carry = 0

        # Trecem de la dreapta la stanga prin numere si adunam pentru a gasi suma
        for i in range(max_len):
            suma_cifre = cifre[numar1[i]] + cifre[numar2[i]] + carry
            if suma_cifre >= baza:
                carry = 1
                suma_cifre -= baza
            else:
                carry = 0
            rezultat += inv_cifre[suma_cifre]

        # Adaugam ultimul carry
        if carry == 1:
            rezultat += inv_cifre[carry]

        # Inversam rezultatul si il returnam
        return rezultat[::-1]

    def scadere(self, numar1, baza1, numar2, baza2, baza):
        '''
        Se realizeaza scaderea a doua numere intr-o baza data. Primul numar este intr-o baza data ca parametru, iar al
        doilea la fel. Bazele pot fi diferite si se va oferi si o alta baza in care se va calcula rezultatul.
        :param numar1: (string) primul numar pentru scadere
        :param baza1: (int) baza primului numar
        :param numar2: (string) al doilea numar pentru scadere
        :param baza2: (int) baza celui de-al doilea numar
        :param baza: (int) baza in care se vor scadea numerele
        :return: se va returna diferenta celor doua numere intr-o baza data
        '''
        numar1 = self.conversie_substitutie_baza_intermediara_impartiri_succesive(numar1, baza1, baza)
        numar2 = self.conversie_substitutie_baza_intermediara_impartiri_succesive(numar2, baza2, baza)
        numar1 = int(numar1)
        numar2 = int(numar2)

        # Initializam rezultatul, carry, unde vom memora transportul, si putere
        rezultat = 0
        carry = 0
        putere = 1

        while numar1 > 0:
            # Memoram ultima cifra a celor doua numere in c1 si c2
            c1 = numar1 % 10
            c2 = numar2 % 10

            # Eliminam ultima cifra din cele doua numere si verificam daca avem transport
            numar1 //= 10
            numar2 //= 10
            aux = c1 - c2 + carry
            if aux < 0:
                carry = -1
                aux += baza
            else:
                carry = 0

            rezultat += aux * putere
            putere *= 10

        return str(rezultat)

    def inmultire_cu_o_cifra(self):
        pass

    def impartire_la_o_cifra(self):
        pass
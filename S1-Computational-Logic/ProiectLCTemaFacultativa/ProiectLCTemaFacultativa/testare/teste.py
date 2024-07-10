class Teste:

    def __init__(self, service):
        self.__service = service

    def __ruleaza_conversie_substitutie_baza_intermediara_impartiri_succesive(self):
        assert self.__service.conversie_substitutie_baza_intermediara_impartiri_succesive('121', 3, 16) == '10'
        assert self.__service.conversie_substitutie_baza_intermediara_impartiri_succesive('FF', 16, 2) == '11111111'
        assert self.__service.conversie_substitutie_baza_intermediara_impartiri_succesive('101010', 2, 16) == '2A'
        assert self.__service.conversie_substitutie_baza_intermediara_impartiri_succesive('1F4A', 16, 10) == '8010'
        assert self.__service.conversie_substitutie_baza_intermediara_impartiri_succesive('101010', 2, 10) == '42'
        assert self.__service.conversie_substitutie_baza_intermediara_impartiri_succesive('42', 10, 16) == '2A'
        assert self.__service.conversie_substitutie_baza_intermediara_impartiri_succesive('123', 4, 5) == '102'

    def __ruleaza_conversie_rapida(self):
        assert self.__service.conversie_rapida('101101', 2, 4) == '231'
        assert self.__service.conversie_rapida('101101', 2, 8) == '55'
        assert self.__service.conversie_rapida('101101', 2, 16) == '2D'
        assert self.__service.conversie_rapida('1211', 4, 2) == '01100101'
        assert self.__service.conversie_rapida('1211', 4, 8) == '145'
        assert self.__service.conversie_rapida('1211', 4, 16) == '65'
        assert self.__service.conversie_rapida('335', 8, 2) == '011011101'
        assert self.__service.conversie_rapida('335', 8, 4) == '03131'
        assert self.__service.conversie_rapida('335', 8, 16) == '0DD'
        assert self.__service.conversie_rapida('DA', 16, 2) == '11011010'
        assert self.__service.conversie_rapida('2D', 16, 4) == '0231'
        assert self.__service.conversie_rapida('DA', 16, 8) == '332'

    def __ruleaza_adunare(self):
        assert self.__service.adunare('32', 4, '2f', 16, 16) == '3D'
        assert self.__service.adunare('7', 9, '11', 3, 2) == '1011'
        assert self.__service.adunare('203', 6, '55', 6, 6) == '302'
        assert self.__service.adunare('6f', 16, '12', 3, 8) == '164'
        assert self.__service.adunare('45', 7, '123', 4, 10) == '60'

    def __ruleaza_scadere(self):
        assert self.__service.scadere('7', 9, '11', 3, 2) == '11'
        assert self.__service.scadere('203', 6, '55', 6, 6) == '104'
        assert self.__service.scadere('6f', 16, '12', 3, 8) == '152'
        assert self.__service.scadere('45', 7, '123', 4, 10) == '6'

    def __ruleaza_inmultire_cu_o_cifra(self):
        pass

    def __ruleaza_impartire_la_o_cifra(self):
        pass

    def ruleaza_toate_testele(self):
        self.__ruleaza_conversie_substitutie_baza_intermediara_impartiri_succesive()
        self.__ruleaza_conversie_rapida()
        self.__ruleaza_adunare()
        self.__ruleaza_scadere()
        self.__ruleaza_inmultire_cu_o_cifra()
        self.__ruleaza_impartire_la_o_cifra()
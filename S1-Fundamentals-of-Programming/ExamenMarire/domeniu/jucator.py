class Jucator:

    def __init__(self, nume, prenume, inaltime, post):
        self.__nume = nume
        self.__prenume = prenume
        self.__inaltime = inaltime
        self.__post = post

    def get_nume(self):
        '''
        numele jucatorului
        :return: numele jucatorului
        '''
        return self.__nume

    def get_prenume(self):
        '''
        prenumele jucatorului
        :return: prenumele jucatorului
        '''
        return self.__prenume

    def get_inaltime(self):
        '''
        inaltimea jucatorului
        :return: inaltimea jucatorului
        '''
        return self.__inaltime

    def get_post(self):
        '''
        postul jucatorului
        :return: postul jucatorului
        '''
        return self.__post

    def set_inaltime(self, inaltime):
        '''
        inaltimea jucatorului
        :param inaltime: int
        :return: inaltimea jucatorului
        '''
        self.__inaltime = inaltime

    def set_post(self, post):
        '''
        postul jucatorului
        :param post: string ('Fundas', 'Pivot', 'Extrema')
        :return: postul jucatorului
        '''
        self.__post = post

    def __eq__(self, other):
        return self.__nume == other.get_nume() and self.__prenume == other.get_prenume()

    def __str__(self):
        return f'{self.__nume},{self.__prenume},{self.__inaltime},{self.__post}'
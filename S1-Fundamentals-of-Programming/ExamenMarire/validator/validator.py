from erori.validation_error import ValidError


class Validare:

    def __init__(self):
        pass

    def valideaza(self, jucator):
        '''
        verifica daca datele introduse sunt valide
        :param jucator: jucator
        :return: -
                 raise ValidError daca cel putin o data nu este corecta
        '''
        erori = ''
        if jucator.get_nume() == '':
            erori += 'nume invalid!\n'
        if jucator.get_prenume() == '':
            erori += 'prenume invalid!\n'
        if jucator.get_inaltime() <= 140:
            erori += 'inaltime invalida!\n'
        if jucator.get_post() != 'Fundas' and jucator.get_post() != 'Pivot' and jucator.get_post() != 'Extrema':
            erori += 'post invalid!\n'
        if len(erori) != 0:
            raise ValidError(erori)
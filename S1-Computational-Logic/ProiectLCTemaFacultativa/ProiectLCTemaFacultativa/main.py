from business.service import Service
from prezentare.consola import UI
from testare.teste import Teste

'''
Creat de Calauz Razvan
'''

if __name__ == '__main__':
    service = Service()
    consola = UI(service)
    teste = Teste(service)
    teste.ruleaza_toate_testele()
    consola.run()
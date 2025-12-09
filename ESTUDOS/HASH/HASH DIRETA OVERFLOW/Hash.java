public class Hash {
    int[] tabela;
    int m1, m2, tamTabela, reserva;
    final int NULO = -1;

    public Hash(int m1, int m2){
        this.m1 = m1;
        this.m2 = m2;
        this.tamTabela = m1 + m2;
        this.reserva = 0;
        for(int i = 0; i < tamTabela; i++){
            tabela[i] = NULO;
        }
    }

    public int h(int elemento){
        return elemento % tamTabela;
    }

    public boolean inserir(int elemento){
        boolean resp = false;
        if (elemento != NULO){
            int pos = h(elemento);
            if(tabela[pos] == NULO){
                tabela[pos] = elemento;
                resp = true;
            } else if (reserva < m2){
                tabela[m1 + reserva] = elemento;
                resp = true;
                reserva++;
            }
        }
        return resp;
    }

    public boolean pesquisar (int elemento){
        boolean resp = false;
        int pos = h(elemento);
        if (tabela[pos] == elemento){
            resp = true;
        } else if (tabela[pos] != NULO){
            for(int i = 0; i < reserva; i++){
                if(tabela[m1 + i] == elemento){
                    resp = true;
                    i = reserva;
                }
            }
        }
        return resp;
    }

    public boolean remover(int elemento){
        boolean resp = false;
        int pos = h(elemento);
        if(tabela[pos] == elemento){
            resp = true;
            int indexCandidatoReserva = -1;
            for(int i = 0; i < reserva; i++){
                if(tabela[m1 + i] != NULO && h(tabela[m1 + i]) == pos){
                    indexCandidatoReserva = i;
                    break;
                }
            }
            if (indexCandidatoReserva == -1) tabela[pos] = NULO;
            else {
                tabela[pos] = tabela[m1 = indexCandidatoReserva];
                for(int i = indexCandidatoReserva; i < reserva; i++){
                    tabela[m1 + i] = tabela[m1 + i + 1];
                }
            }
            reserva--;
        } else if(tabela[pos] != NULO){
            int indiceReserva = -1;
            for(int i = 0; i < reserva; i++){
                if(tabela[m1 + i] == elemento){
                    indiceReserva = i;
                    break;
                }
            }
            if (indiceReserva != -1){
                resp = true;
                for(int i = indiceReserva; i < reserva; i++){
                    tabela[m1 + i] = tabela[m1 + i + 1];
                }
                reserva--;
            }
        }
        return resp;
    }

}

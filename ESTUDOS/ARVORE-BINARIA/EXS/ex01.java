int numPares() {
    return numPares(raiz);
}

int numPares(No i){
    if (i == null){
        return 0;
    }else{
        if(i.elemento % 2 == 0){
            return 1 + numPares(i.esq) + numPares(i.dir);
        }
        else{
            return 0 + numPares(i.esq) + numPares(i.dir);
        }
    }
}
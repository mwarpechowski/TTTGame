package pl.mwinc.demo.ttt.model;

public enum PlayerSymbol {
    X,
    O;

    public static PlayerSymbol parseOrNull(char ch){
        switch(Character.toUpperCase(ch)) {
            case 'X': return X;
            case 'O': return O;
            default: return  null;
        }
    }
}

/*
 * http://enos.itcollege.ee/~jpoial/algoritmid/adt.html
 */

public class Magasin {

    /** Peameetod (silumiseks). */
    public static void main (String[] argum) {
        Magasin m = new Magasin();
        System.out.println (m);
        m.push (1);
        System.out.println (m);
        m.push (3);
        System.out.println (m);
        m.push (6);
        System.out.println (m);
        m.push (2);
        System.out.println (m);
        m.op ("/");
        System.out.println (m);
        m.op ("*");
        System.out.println (m);
        m.op ("-");
        System.out.println (m);
        int tulemus = m.pop();
        System.out.println (m);
        System.out.println (tulemus);
        Magasin koopia = m;
        System.out.println (koopia.equals (m));
        System.out.println (m); System.out.println (koopia);
        koopia = (Magasin)m.clone();
        System.out.println (koopia.equals (m));
        System.out.println (m); System.out.println (koopia);
        m.push (6);
        koopia.push (3);
        System.out.println (koopia.equals (m));
        System.out.println (m); System.out.println (koopia);
        m.pop();
        System.out.println (koopia.equals (m));
        System.out.println (m); System.out.println (koopia);
    } // main


    /** magasin ise massiivina. */
    private int [] mag;

    /** tipu indeks. */
    private int SP;

    /** vaikekonstruktor teeb 10-elemendilise magasini. */
    Magasin () {
        this (10);
    } // konstruktor

    /** etteantud elementide arvuga magasini konstruktor. */
    Magasin (int suurus) {
        mag = new int [suurus];
        SP = -1; // tunnus, et magasin on tyhi
    } // konstruktor

    /** koopia loomine. */
    @Override
    public Object clone() {
        Magasin tmp = new Magasin (mag.length);
        tmp.SP = SP;
        if (SP >= 0)
            for (int i=0; i<=SP; i++)
                tmp.mag [i] = mag [i];
        return tmp;
    } // clone

    /** alata"itumise kontroll. */
    public boolean magTyhi() {
        return (SP == -1);
    } // magTyhi

    /** yleta"itumise kontroll. */
    public boolean magLohki() {
        return ((SP + 1) >= mag.length);
    } // magLohki

    /** lisamine magasini. */
    public void push (int a) {
        if (magLohki())
            throw new IndexOutOfBoundsException ("magasini yletaitumine");
        SP += 1; // increment
        mag [SP] = a;
    } // push

    /** eemaldamine magasinist. */
    public int pop() {
        if (magTyhi())
            throw new IndexOutOfBoundsException ("magasini alataitumine");
        int tmp = mag [SP];
        SP -= 1; // decrement
        return tmp;
    } // pop

    /** aritmeetiline tehe tipus olevate elementide vahel (na"ide). */
    public void op (String s) {
        int op2 = pop();
        int op1 = pop();
        if (s.equals ("+")) push (op1 + op2);
        if (s.equals ("-")) push (op1 - op2);
        if (s.equals ("*")) push (op1 * op2);
        if (s.equals ("/")) push (op1 / op2);
    } // op

    /** tipu lugemine eemaldamiseta. */
    public int tos() {
        if (magTyhi())
            throw new IndexOutOfBoundsException ("magasini alataitumine");
        return mag [SP];
    } // tos

    /** magasinide v6rdsus. */
    public boolean equals (Object o) {
        if (((Magasin)o).SP != SP) return false;
        if (magTyhi()) return true; // teine ka tyhi!
        for (int i=0; i<=SP; i++)
            if (((Magasin)o).mag[i] != mag [i]) return false;
        return true;
    } // equals

    /** teisendus s6neks (tipp paremal). */
    public String toString() {
        if (magTyhi()) return "Tyhi";
        StringBuffer b = new StringBuffer();
        for (int i=0; i<=SP; i++)
            b.append (String.valueOf (mag [i]) + " ");
        return b.toString();
    } // toString

}
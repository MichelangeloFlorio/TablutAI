package it.unibo.ai.didattica.competition.tablut.tablutai.heuristic;

public class AnalysisResult {

    public static final int VINCE_BIANCO = 1;
    public static final int VINCE_NERO = 2;
    public static final int NESSUN_VINCITORE = 0;

    private int nBianchi = 0;
    private int nNeri = 0;
    private int vieLibereRe = 0;
    private int neriInLineaRe = 0;
    private int neriAttaccatiRe = 0;
    private int vincitore = NESSUN_VINCITORE;

    public AnalysisResult(){}

    public AnalysisResult(int nBianchi, int nNeri, int vieLibereRe, int neriInLineaRe, int neriAttaccatiRe) {
        this.nBianchi = nBianchi;
        this.nNeri = nNeri;
        this.vieLibereRe = vieLibereRe;
        this.neriInLineaRe = neriInLineaRe;
        this.neriAttaccatiRe = neriAttaccatiRe;
    }

    public int getnBianchi() {
        return nBianchi;
    }

    public void setnBianchi(int nBianchi) {
        this.nBianchi = nBianchi;
    }

    public int getnNeri() {
        return nNeri;
    }

    public void setnNeri(int nNeri) {
        this.nNeri = nNeri;
    }

    public int getVieLibereRe() {
        return vieLibereRe;
    }

    public void setVieLibereRe(int vieLibereRe) {
        this.vieLibereRe = vieLibereRe;
    }

    public int getNeriInLineaRe() {
        return neriInLineaRe;
    }

    public void setNeriInLineaRe(int neriInLineaRe) {
        this.neriInLineaRe = neriInLineaRe;
    }

    public int getNeriAttaccatiRe() {
        return neriAttaccatiRe;
    }

    public void setNeriAttaccatiRe(int neriAttaccatiRe) {
        this.neriAttaccatiRe = neriAttaccatiRe;
    }

    public int getVincitore() {
        return vincitore;
    }

    public void setVincitore(int vincitore) {
        this.vincitore = vincitore;
    }
}

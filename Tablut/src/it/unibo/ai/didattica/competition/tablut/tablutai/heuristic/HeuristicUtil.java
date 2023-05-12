package it.unibo.ai.didattica.competition.tablut.tablutai.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.Arrays;
import java.util.List;

public class HeuristicUtil {

    private static final List<String> escapes = Arrays.asList("a2", "a3", "a7", "a8", "b1", "b9", "c1", "c9", "g1", "g9", "h1", "h9", "i2", "i3",
            "i7", "i8"); //lista delle caselle con le vie di fuga

    private State state;
    private int xTrone = 4;
    private int yTrone = 4;

    private static final int SINISTRA_RE = 2;
    private static final int DESTRA_RE = 3;
    private static final int SOPRA_RE = 5;
    private static final int SOTTO_RE = 7;



    public HeuristicUtil(State state){
        this.state=state;
    }

    public AnalysisResult evalState() {
        int nBianchi = 0;
        int nNeri = 0;
        boolean reNonTrovato = true;
        AnalysisResult res = new AnalysisResult();


        for (int i = 0; i < state.getBoard().length; i++) {
            for (int j = 0; j < state.getBoard()[i].length; j++) {

                // CALCOLO SITUAZIONE PEDINE SULLA BOARD
                if (state.getPawn(i, j).equalsPawn(State.Pawn.WHITE.toString())) {
                    nBianchi++;
                } else if (state.getPawn(i, j).equalsPawn(State.Pawn.KING.toString())) {
                    reNonTrovato=false;
                    //in caso di re valutare come la sua riga e colonna
                    /*
                    if(escapes.contains(state.getBox(i, j))){ //se re in casella di fuga allora il risultato è vince bianco.
                        res.setVincitore(AnalysisResult.VINCE_BIANCO);
                        return res;
                    }
                    //se re non fuggito continua a valutare lo stato
                     */
                    checkRowColKing(res, i, j);

                    /*
                    if(res.getVincitore()==AnalysisResult.VINCE_NERO){ //se il re è accerchiato in maniera letale ritorna il vincitore
                        return res;
                    }

                     */

                } else if (state.getPawn(i, j).equalsPawn(State.Pawn.BLACK.toString())) {
                    nNeri++;
                }
            }
        }
        if(reNonTrovato){ //il re è stato eliminato, vince nero
            res.setVincitore(AnalysisResult.VINCE_NERO);
            return res;
        }
        res.setnBianchi(nBianchi);
        res.setnNeri(nNeri);
        return res;
    }

    private void checkRowColKing(AnalysisResult res, int x, int y){
        //controlla la riga e la colonna col re per vedere se ha vie di fuga libere e quanti neri lo accerchiano
        int vieLibereRe = 0;
        int neriInLineaRe = 0;
        int neriAttaccatiRe = 0;
        boolean nonContato = true;
        int situazioneAccerchiamentoRe = 1;
        //controllo riga
        for (int i = 0; i < x; i++) { //a sinistra del re
            if(nonContato && state.getPawn(i, y).equalsPawn(State.Pawn.BLACK.toString())){
                neriInLineaRe++;
                nonContato=false;
            }
            if(i == x-1 && state.getPawn(i, y).equalsPawn(State.Pawn.BLACK.toString())){
                neriAttaccatiRe++;
                situazioneAccerchiamentoRe*= SINISTRA_RE;
            }
        }
        if(nonContato){
            vieLibereRe++;
        }
        nonContato = true;
        for (int i = x; i < state.getBoard().length; i++) { //a destra del re
            if(nonContato && state.getPawn(i, y).equalsPawn(State.Pawn.BLACK.toString())){
                neriInLineaRe++;
                nonContato=false;
            }
            if(i == x+1 && state.getPawn(i, y).equalsPawn(State.Pawn.BLACK.toString())){
                neriAttaccatiRe++;
                situazioneAccerchiamentoRe*= DESTRA_RE;
            }
        }
        if(nonContato){
            vieLibereRe++;
        }


        //controllo colonna
        nonContato = true;
        for (int j = 0; j < y; j++) { //sopra il re
            if(nonContato && state.getPawn(x, j).equalsPawn(State.Pawn.BLACK.toString())){
                neriInLineaRe++;
                nonContato=false;
            }
            if(j == y-1 && state.getPawn(x, j).equalsPawn(State.Pawn.BLACK.toString())){
                neriAttaccatiRe++;
                situazioneAccerchiamentoRe*= SOPRA_RE;
            }
        }
        if(nonContato){
            vieLibereRe++;
        }
        nonContato = true;
        for (int j = y; j < state.getBoard().length; j++) { //sotto il re
            if(nonContato && state.getPawn(x, j).equalsPawn(State.Pawn.BLACK.toString())){
                neriInLineaRe++;
                nonContato=false;
            }
            if(j == y+1 && state.getPawn(x, j).equalsPawn(State.Pawn.BLACK.toString())){
                neriAttaccatiRe++;
                situazioneAccerchiamentoRe*= SOTTO_RE;
            }
        }
        if(nonContato){
            vieLibereRe++;
        }
        /*
        if(reAmmazzato(x,y,situazioneAccerchiamentoRe)) {
            res.setVincitore(AnalysisResult.VINCE_NERO);
        }

         */
        res.setNeriAttaccatiRe(neriAttaccatiRe);
        res.setNeriInLineaRe(neriInLineaRe);
        res.setVieLibereRe(vieLibereRe);
    }

    /*
    boolean reAmmazzato(int x, int y, int accerchiamento){
        if(x==xTrone && y==yTrone){ //re in trono
            return accerchiamento == SINISTRA_RE * DESTRA_RE * SOPRA_RE * SOTTO_RE;
        }
        else if(x==xTrone && yTrone == y+1){ //trono sotto re
            return accerchiamento == SINISTRA_RE * DESTRA_RE * SOPRA_RE;
        }
        else if(x==xTrone && yTrone == y-1){ //trono sopra re
            return accerchiamento == SINISTRA_RE * DESTRA_RE * SOTTO_RE;
        }
        else if(y==yTrone && yTrone == y+1){ //trono destra re
            return accerchiamento == SINISTRA_RE * SOPRA_RE * SOTTO_RE;
        }
        else if(y==yTrone && yTrone == y-1){ //trono sinistra re
            return accerchiamento == DESTRA_RE * SOPRA_RE * SOTTO_RE;
        }
        else { //re lontano dal trono
            return (accerchiamento == SINISTRA_RE * DESTRA_RE) ||  (accerchiamento == SOPRA_RE * SOTTO_RE);
        }
    }

     */
}

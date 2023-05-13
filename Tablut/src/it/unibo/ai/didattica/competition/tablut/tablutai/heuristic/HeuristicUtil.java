package it.unibo.ai.didattica.competition.tablut.tablutai.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

import java.util.Arrays;
import java.util.List;

public class HeuristicUtil {
/*
    private static final List<String> escapes = Arrays.asList("a2", "a3", "a7", "a8", "b1", "b9", "c1", "c9", "g1", "g9", "h1", "h9", "i2", "i3",
            "i7", "i8"); //lista delle caselle con le vie di fuga


 */
    private State state;
    private int xTrone = 4;
    private int yTrone = 4;


    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

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

                    checkRowColKing(res, i, j);


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
        boolean libero = true;
        //controllo riga
        for (int i = 0; i < x; i++) { //a sinistra del re
            if(nonContato && state.getPawn(i, y).equalsPawn(State.Pawn.BLACK.toString())){
                neriInLineaRe++;
                nonContato=false;
            }
            if(i == x-1 && state.getPawn(i, y).equalsPawn(State.Pawn.BLACK.toString())){
                neriAttaccatiRe++;
            }
            if(!state.getPawn(i, y).equalsPawn(State.Pawn.EMPTY.toString())){
                libero=false;
            }
        }
        if(libero){
            vieLibereRe++;
        }
        libero = true;
        nonContato = true;
        for (int i = x; i < state.getBoard().length; i++) { //a destra del re
            if(nonContato && state.getPawn(i, y).equalsPawn(State.Pawn.BLACK.toString())){
                neriInLineaRe++;
                nonContato=false;
            }
            if(i == x+1 && state.getPawn(i, y).equalsPawn(State.Pawn.BLACK.toString())){
                neriAttaccatiRe++;
            }
            if(!state.getPawn(i, y).equalsPawn(State.Pawn.EMPTY.toString())){
                libero=false;
            }
        }
        if(libero){
            vieLibereRe++;
        }


        //controllo colonna
        libero=true;
        nonContato = true;
        for (int j = 0; j < y; j++) { //sopra il re
            if(nonContato && state.getPawn(x, j).equalsPawn(State.Pawn.BLACK.toString())){
                neriInLineaRe++;
                nonContato=false;
            }
            if(j == y-1 && state.getPawn(x, j).equalsPawn(State.Pawn.BLACK.toString())){
                neriAttaccatiRe++;
            }
            if(!state.getPawn(x, j).equalsPawn(State.Pawn.EMPTY.toString())){
                libero=false;
            }
        }
        if(libero){
            vieLibereRe++;
        }
        libero = true;
        nonContato = true;
        for (int j = y; j < state.getBoard().length; j++) { //sotto il re
            if(nonContato && state.getPawn(x, j).equalsPawn(State.Pawn.BLACK.toString())){
                neriInLineaRe++;
                nonContato=false;
            }
            if(j == y+1 && state.getPawn(x, j).equalsPawn(State.Pawn.BLACK.toString())){
                neriAttaccatiRe++;
            }
            if(!state.getPawn(x, j).equalsPawn(State.Pawn.EMPTY.toString())){
                libero=false;
            }
        }
        if(libero){
            vieLibereRe++;
        }

        res.setNeriAttaccatiRe(neriAttaccatiRe);
        res.setNeriInLineaRe(neriInLineaRe);
        res.setVieLibereRe(vieLibereRe);
    }


}

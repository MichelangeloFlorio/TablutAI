package it.unibo.ai.didattica.competition.tablut.tablutai.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;
import it.unibo.ai.didattica.competition.tablut.tablutai.domain.Coordinate;

import java.util.Arrays;
import java.util.List;

public class HeuristicUtil {

    private static final List<String> camps = Arrays.asList("a4", "a5", "a6", "b5", "d1", "e1", "f1", "e2", "i4", "i5", "i6", "h5", "d9",
						"e9", "f9", "e8");




    public static AnalysisResult evalState(State state) {
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

                    checkRowColKing(res, i, j, state);


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

    private static void checkRowColKing(AnalysisResult res, int x, int y, State state){
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
            if(!state.getPawn(i, y).equalsPawn(State.Pawn.EMPTY.toString()) || camps.contains(state.getBox(i, y))){
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
            if(!state.getPawn(i, y).equalsPawn(State.Pawn.EMPTY.toString()) || camps.contains(state.getBox(i, y))){
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
            if(!state.getPawn(x, j).equalsPawn(State.Pawn.EMPTY.toString()) || camps.contains(state.getBox(x, j))){
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
            if(!state.getPawn(x, j).equalsPawn(State.Pawn.EMPTY.toString()) || camps.contains(state.getBox(x, j))){
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

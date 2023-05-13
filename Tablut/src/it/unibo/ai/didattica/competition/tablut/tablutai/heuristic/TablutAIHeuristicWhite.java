package it.unibo.ai.didattica.competition.tablut.tablutai.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class TablutAIHeuristicWhite {

    //la nostra funzione euristica !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    private static final int PESO_PEDONI_BIANCHI = 2;
    private static final int PESO_PEDONI_NERI = -4;
    private static final int PESO_STRADA_LIBERA_RE = 40;
    private static final int PESO_PEDONI_IN_LINEA_RE = -5;
    private static final int PESO_PEDONI_ATTACCATI_RE = -10;
    private static final int PESO_RE_FUGGITO = 200;
    private static final int PESO_RE_UCCISO = -200;

    private State state;

    public TablutAIHeuristicWhite (State state) {
        this.state=state;
    }

    public double evaluateState() {
        if(state.getTurn().equalsTurn(String.valueOf(State.Turn.BLACKWIN))){
            return PESO_RE_UCCISO;
        }
        else if(state.getTurn().equalsTurn(String.valueOf(State.Turn.WHITEWIN)))
            return PESO_RE_FUGGITO;

        AnalysisResult res = HeuristicUtil.evalState(state);

        return  res.getnBianchi()*PESO_PEDONI_BIANCHI +
                res.getnNeri()*PESO_PEDONI_NERI +
                res.getNeriAttaccatiRe()*PESO_PEDONI_ATTACCATI_RE +
                res.getNeriInLineaRe()*PESO_PEDONI_IN_LINEA_RE +
                res.getVieLibereRe()*PESO_STRADA_LIBERA_RE;

    }
}

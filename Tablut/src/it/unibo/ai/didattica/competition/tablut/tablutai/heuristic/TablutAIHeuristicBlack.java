package it.unibo.ai.didattica.competition.tablut.tablutai.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class TablutAIHeuristicBlack {

    //la nostra funzione euristica !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    private static final int PESO_PEDONI_BIANCHI = -2;
    private static final int PESO_PEDONI_NERI = 1;
    private static final int PESO_STRADA_LIBERA_RE = -40;
    private static final int PESO_PEDONI_IN_LINEA_RE = 10;
    private static final int PESO_PEDONI_ATTACCATI_RE = 30;
    private static final int PESO_RE_FUGGITO = -200;
    private static final int PESO_RE_UCCISO = 200;


    private State state;
    private HeuristicUtil util;

    public TablutAIHeuristicBlack (State state) {
        this.state=state;
        util = new HeuristicUtil(state);
    }

    public double evaluateState() {
        if(state.getTurn().equalsTurn(String.valueOf(State.Turn.BLACKWIN))){
            return PESO_RE_UCCISO;
        }
        else if(state.getTurn().equalsTurn(String.valueOf(State.Turn.WHITEWIN)))
            return PESO_RE_FUGGITO;

        AnalysisResult res = util.evalState();
        int sum = 0;
        /*
        int vincitore = res.getVincitore();
        if(vincitore==AnalysisResult.VINCE_NERO)
            return PESO_RE_UCCISO;
        else if(vincitore==AnalysisResult.VINCE_BIANCO)
            return PESO_RE_FUGGITO;

         */

        return  res.getnBianchi()*PESO_PEDONI_BIANCHI +
                res.getnNeri()*PESO_PEDONI_NERI +
                res.getNeriAttaccatiRe()*PESO_PEDONI_ATTACCATI_RE +
                res.getNeriInLineaRe()*PESO_PEDONI_IN_LINEA_RE +
                res.getVieLibereRe()*PESO_STRADA_LIBERA_RE;

    }
}

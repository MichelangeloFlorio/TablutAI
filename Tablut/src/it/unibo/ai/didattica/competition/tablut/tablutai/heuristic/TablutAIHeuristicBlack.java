package it.unibo.ai.didattica.competition.tablut.tablutai.heuristic;

import it.unibo.ai.didattica.competition.tablut.domain.State;

public class TablutAIHeuristicBlack {

    //la nostra funzione euristica !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!

    private static final int PESO_PEDONI_BIANCHI_ELIMINATI = 4; //cala valore in base a bianchi vivi
    private static final int PESO_PEDONI_NERI = 2;
    private static final int PESO_STRADA_LIBERA_RE_LASCIATE = 40; //cala valore in base a vie re libere
    private static final int PESO_PEDONI_IN_LINEA_RE = 5;
    private static final int PESO_PEDONI_ATTACCATI_RE = 10;
    private static final int PESO_RE_FUGGITO = 0;
    private static final int PESO_RE_UCCISO = 200;


    private State state;

    public TablutAIHeuristicBlack (State state) {
        this.state=state;
    }

    public double evaluateState() {
        if(state.getTurn().equalsTurn(String.valueOf(State.Turn.BLACKWIN))){
            return PESO_RE_UCCISO;
        }
        else if(state.getTurn().equalsTurn(String.valueOf(State.Turn.WHITEWIN)))
            return PESO_RE_FUGGITO;

        AnalysisResult res = HeuristicUtil.evalState(state);

        return  (16-res.getnBianchi())*PESO_PEDONI_BIANCHI_ELIMINATI +
                res.getnNeri()*PESO_PEDONI_NERI +
                res.getNeriAttaccatiRe()*PESO_PEDONI_ATTACCATI_RE +
                res.getNeriInLineaRe()*PESO_PEDONI_IN_LINEA_RE +
                (4-res.getVieLibereRe())*PESO_STRADA_LIBERA_RE_LASCIATE;

    }
}

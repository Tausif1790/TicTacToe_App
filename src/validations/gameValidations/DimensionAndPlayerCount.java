package validations.gameValidations;

import exceptions.DimensionAndPlayerCountMismatch;
import exceptions.InvalidPlayerCountException;
import models.Player;

import java.util.List;

public class DimensionAndPlayerCount {
    public static void validate(int dimension, List<Player> players) throws Exception {
        if(players.size() <= 1 ){
            throw new InvalidPlayerCountException("Player count is invalid");
        }else if(players.size() >= dimension){
            throw new DimensionAndPlayerCountMismatch("Player count should be lesser than dimension");
        }
    }
}

    package factories;

import models.BotPlayingDifficulty;
import strategies.BotPlayingStrategy;
import strategies.EasyBotPlayingStrategy;
import strategies.MediumBotPlayingStrategy;

    public class BotPlayingStrategyFactory {
    public static BotPlayingStrategy getBotPlayingStrategy(BotPlayingDifficulty difficulty){
        if(difficulty.equals(BotPlayingDifficulty.EASY)){
            return new EasyBotPlayingStrategy();
        }
        else if(difficulty.equals(BotPlayingDifficulty.MEDIUM)){
            return new MediumBotPlayingStrategy();
        }
//        else if(difficulty.equals(BotPlayingDifficulty.HARD)){
//            return new HardBotPlayingStrategy();
//        }

        return null;
    }
}

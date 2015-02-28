package ai.commands.general;

import ai.commands.AiCommand;

/**
 * 2/28/2015
 */
public class GotoCommand extends AiCommand {
    private int destinationLine;
    public GotoCommand(int destination) {
        this.destinationLine = destination;
    }

    @Override
    public int run(int[] localData, boolean[] flags) {
        return destinationLine;
    }

    @Override
    public String toString() {
        return "Goto "+destinationLine;
    }
}
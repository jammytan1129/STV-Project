package adapter.scriptGenerator;

import adapter.Instruction;
import useCase.command.Command;

import java.util.List;

public interface ICommandMapper {

    @Deprecated
    Command mappingFrom(String instruction);

    Command mappingFrom(Instruction instruction);

    List<Command> mappingFrom(List<Instruction> instructions);
}
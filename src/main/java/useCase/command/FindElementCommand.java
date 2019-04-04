package useCase.command;

import adapter.device.DeviceDriver;

public class FindElementCommand extends Command {

    public FindElementCommand(DeviceDriver deviceDriver, String xPath) {
        super(deviceDriver, xPath);
    }

    @Override
    public void execute() {
        this.deviceDriver.findElement(this.xPath);
    }
}

package pers.zhangyang.easyguishop.executor;

import org.bukkit.command.CommandSender;
import org.jetbrains.annotations.NotNull;
import pers.zhangyang.easyguishop.exception.NotExistIconException;
import pers.zhangyang.easyguishop.service.CommandService;
import pers.zhangyang.easyguishop.service.impl.CommandServiceImpl;
import pers.zhangyang.easyguishop.yaml.MessageYaml;
import pers.zhangyang.easyguishop.easylibrary.base.ExecutorBase;
import pers.zhangyang.easyguishop.easylibrary.util.MessageUtil;
import pers.zhangyang.easyguishop.easylibrary.util.TransactionInvocationHandler;

public class DeleteIconExecutor extends ExecutorBase {
    public DeleteIconExecutor(@NotNull CommandSender sender, String cmdName, @NotNull String[] args) {
        super(sender, cmdName, args);
    }

    @Override
    protected void run() {
        if (args.length != 1) {
            return;
        }

        CommandService guiService = (CommandService) new TransactionInvocationHandler(new CommandServiceImpl()).getProxy();
        try {
            guiService.deleteIcon(args[0]);
        } catch (NotExistIconException e) {
            MessageUtil.sendMessageTo(sender, MessageYaml.INSTANCE.getStringList("message.chat.notExistIconWhenDeleteIcon"));
            return;
        }
        MessageUtil.sendMessageTo(sender, MessageYaml.INSTANCE.getStringList("message.chat.deleteIcon"));
    }
}

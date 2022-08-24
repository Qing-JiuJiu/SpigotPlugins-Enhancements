package com.yishian;

import com.yishian.auxiliary.*;
import com.yishian.common.CommandEnum;
import com.yishian.common.PluginCommonCommand;
import com.yishian.customjoinandleave.CustomJoinAndLeaveListener;
import com.yishian.joinserverwelcome.JoinServerWelcomeListener;
import com.yishian.autosendservermessages.AutoSendServerMessagesRunnable;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * @author XinQi
 */
public final class Main extends JavaPlugin {

    /**
     * 这是插件启动时执行的方法
     */
    @Override
    public void onEnable() {

        //如果没读取到配置文件，保存默认配置文件到插件目录
        saveDefaultConfig();
        FileConfiguration config = getConfig();

        //启动服务器时发送插件消息
        this.getLogger().info("欢迎使用本插件，插件制作者QQ:592342403");

        //注册定时任务
        ConfigurationSection autoSendServerMessagesconfigurationSection = config.getConfigurationSection("auto-send-server-messages");
        if (autoSendServerMessagesconfigurationSection.getBoolean("enable")){
            int sendTime = autoSendServerMessagesconfigurationSection.getInt("time")*20;
            //按照tick时间计算，1tick=0.05s，20tick=1s
            new AutoSendServerMessagesRunnable().runTaskTimer(this,0,sendTime);
        }

        //服务注册监听事件
        if (config.getConfigurationSection("join-and-leave-server-message").getBoolean("enable")) {
            getServer().getPluginManager().registerEvents(new CustomJoinAndLeaveListener(), this);
        }
        if (config.getConfigurationSection("join-server-welcome").getBoolean("enable")) {
            getServer().getPluginManager().registerEvents(new JoinServerWelcomeListener(), this);
        }


        //服务注册指令
        //重载配置文件
        PluginCommand reloadCommand = getCommand(CommandEnum.PLUGHIN_NAME.getCommand());
        reloadCommand.setPermission(CommandEnum.RELOAD_CONFIG_PERMISSION.getCommand());
        reloadCommand.setExecutor(new PluginCommonCommand());

        //恢复生命值注册
        PluginCommand healCommand = getCommand(AuxiliaryCommandEnum.HEAL_COMMAND.getCommand());
        healCommand.setPermission(AuxiliaryCommandEnum.HEAL_PERMISSION.getCommand());
        healCommand.setExecutor(new HealCommand());

        //恢复饱食度注册
        PluginCommand feedCommand = getCommand(AuxiliaryCommandEnum.FEED_COMMAND.getCommand());
        feedCommand.setPermission(AuxiliaryCommandEnum.FEED_PERMISSION.getCommand());
        feedCommand.setExecutor(new FeedCommand());

        //恢复生命值和饱食度完全恢复
        PluginCommand healAndFeedCommand = getCommand(AuxiliaryCommandEnum.HEAL_AND_FEED_COMMAND.getCommand());
        healAndFeedCommand.setPermission(AuxiliaryCommandEnum.HEAL_PERMISSION.getCommand());
        healAndFeedCommand.setPermission(AuxiliaryCommandEnum.FEED_PERMISSION.getCommand());
        healAndFeedCommand.setExecutor(new HealAndFeedCommand());

        //开关飞行
        PluginCommand flyCommand = getCommand(AuxiliaryCommandEnum.FLY_COMMAND.getCommand());
        flyCommand.setPermission(AuxiliaryCommandEnum.FLY_PERMISSION.getCommand());
        flyCommand.setExecutor(new FlyCommand());

        //修改飞行速度
        PluginCommand flySpeedCommand = getCommand(AuxiliaryCommandEnum.FLY_SPEED_COMMAND.getCommand());
        flySpeedCommand.setPermission(AuxiliaryCommandEnum.FLY_SPEED_PERMISSION.getCommand());
        flySpeedCommand.setExecutor(new FlySpeedCommand());

        //修改移动速度
        PluginCommand walkSpeedCommand = getCommand(AuxiliaryCommandEnum.WALK_SPEED_COMMAND.getCommand());
        walkSpeedCommand.setPermission(AuxiliaryCommandEnum.WALK_SPEED_PERMISSION.getCommand());
        walkSpeedCommand.setExecutor(new WalkSpeedCommand());
    }

    /**
     * 这是插件停止时执行的方法
     */
    @Override
    public void onDisable() {
    }
}

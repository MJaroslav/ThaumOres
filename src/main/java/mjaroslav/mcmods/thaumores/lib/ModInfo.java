package mjaroslav.mcmods.thaumores.lib;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ModInfo {
    public static final String MOD_ID = "thaumores";
    public static final String NAME = "ThaumOres";
    public static final String VERSION = "1.6.0";
    public static final String GUI_FACTORY = "mjaroslav.mcmods.thaumores.client.gui.GuiFactory";
    public static final String DEPENDENCIES = "required-after:Thaumcraft@[4.2.3.5,);required-after:mjutils@[1.6.0,);";

    public static final String SERVER_PROXY = "mjaroslav.mcmods.thaumores.common.CommonProxy";
    public static final String CLIENT_PROXY = "mjaroslav.mcmods.thaumores.client.ClientProxy";

    public static final Logger LOG = LogManager.getLogger(NAME);
}

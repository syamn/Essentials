package com.earth2me.essentials.commands;

import static com.earth2me.essentials.I18n._;
import com.earth2me.essentials.User;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;


public class Commandbreak extends EssentialsCommand
{
	public Commandbreak()
	{
		super("break");
	}

	//TODO: Switch to use util class
	@Override
	public void run(final Server server, final User user, final String commandLabel, final String[] args) throws Exception
	{
		final Block block = user.getTargetBlock(null, 20);
		if (block == null)
		{
			throw new NoChargeException();
		}
		if (block.getType() == Material.AIR)
		{
			throw new NoChargeException();
		}
		if (block.getType() == Material.BEDROCK && !user.isAuthorized("essentials.break.bedrock"))
		{
			throw new Exception(_("noBreakBedrock"));
		}
		final BlockBreakEvent event = new BlockBreakEvent(block, user.getBase());
		server.getPluginManager().callEvent(event);
		if (event.isCancelled())
		{
			throw new NoChargeException();
		}
		else
		{
			block.setType(Material.AIR);
		}
	}
}
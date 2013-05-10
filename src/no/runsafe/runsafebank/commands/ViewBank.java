package no.runsafe.runsafebank.commands;

import no.runsafe.framework.command.player.PlayerCommand;
import no.runsafe.framework.server.RunsafeServer;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.runsafebank.BankHandler;

import java.util.HashMap;

public class ViewBank extends PlayerCommand
{
	public ViewBank(BankHandler bankHandler)
	{
		super("viewbank", "Opens a players bank", "runsafe.bank.view", "player");
		this.bankHandler = bankHandler;
	}

	@Override
	public String OnExecute(RunsafePlayer executor, HashMap<String, String> parameters)
	{
		RunsafePlayer player = RunsafeServer.Instance.getPlayer(parameters.get("player"));

		if (player != null)
			this.bankHandler.openPlayerBank(executor, player);
		else
			 executor.sendColouredMessage("&cThe player you are looking for does not exist.");

		return null;
	}

	private BankHandler bankHandler;
}
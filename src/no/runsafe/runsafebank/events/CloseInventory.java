package no.runsafe.runsafebank.events;

import no.runsafe.framework.event.inventory.IInventoryClosed;
import no.runsafe.framework.server.event.inventory.RunsafeInventoryCloseEvent;
import no.runsafe.framework.server.player.RunsafePlayer;
import no.runsafe.runsafebank.BankHandler;

public class CloseInventory implements IInventoryClosed
{
	public CloseInventory(BankHandler bankHandler)
	{
		this.bankHandler = bankHandler;
	}

	@Override
	public void OnInventoryClosed(RunsafeInventoryCloseEvent event)
	{
		RunsafePlayer player = event.getPlayer();
		if (this.bankHandler.isViewingBank(player))
		{
			this.bankHandler.savePlayerBank(this.bankHandler.getViewingBank(player), event.getInventory());
			this.bankHandler.closePlayerBank(player);
		}
	}

	private BankHandler bankHandler;
}
package no.runsafe.runsafebank;

import no.runsafe.framework.api.database.IDatabase;
import no.runsafe.framework.api.database.IRow;
import no.runsafe.framework.api.database.Repository;
import no.runsafe.framework.minecraft.RunsafeServer;
import no.runsafe.framework.minecraft.inventory.RunsafeInventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BankRepository extends Repository
{
	public BankRepository(IDatabase database)
	{
		this.database = database;
	}

	@Override
	public String getTableName()
	{
		return "runsafeBanks";
	}

	public RunsafeInventory get(String playerName)
	{
		IRow data = database.QueryRow(
			"SELECT bankInventory FROM runsafeBanks WHERE playerName=?",
			playerName
		);

		RunsafeInventory inventory = RunsafeServer.Instance.createInventory(null, 54, String.format("%s's Bank Vault", playerName));

		if (data != null)
			inventory.unserialize(data.String("bankInventory"));

		return inventory;
	}

	public void update(String bankOwner, RunsafeInventory inventory)
	{
		String inventoryString = inventory.serialize();
		database.Execute(
			"INSERT INTO `runsafeBanks` (playerName, bankInventory) VALUES(?,?) " +
				"ON DUPLICATE KEY UPDATE bankInventory = ?",
			bankOwner, inventoryString, inventoryString
		);
	}

	@Override
	public HashMap<Integer, List<String>> getSchemaUpdateQueries()
	{
		HashMap<Integer, List<String>> versions = new HashMap<Integer, List<String>>();
		ArrayList<String> sql = new ArrayList<String>();
		sql.add(
				"CREATE TABLE `runsafeBanks` (" +
						"`playerName` varchar(50) NOT NULL," +
						"`bankInventory` longtext," +
						"PRIMARY KEY (`playerName`)" +
						")"
		);
		versions.put(1, sql);

		return versions;
	}

	private IDatabase database;
}

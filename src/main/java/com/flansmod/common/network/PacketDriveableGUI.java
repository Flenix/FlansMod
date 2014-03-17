package com.flansmod.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.flansmod.common.FlansMod;
import com.flansmod.common.driveables.EntityDriveable;
import com.flansmod.common.driveables.EntitySeat;

import cpw.mods.fml.relauncher.Side;

public class PacketDriveableGUI extends PacketBase
{
	public int guiID;
	
	public PacketDriveableGUI() {}

	public PacketDriveableGUI(int i)
	{
		guiID = i;
	}
		
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) 
	{
		data.writeInt(guiID);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) 
	{
		guiID = data.readInt();
	}

	@Override
	public void handleServerSide(EntityPlayerMP playerEntity) 
	{
		if(playerEntity.ridingEntity != null && playerEntity.ridingEntity instanceof EntitySeat)
		{
			EntityDriveable d = ((EntitySeat)playerEntity.ridingEntity).driveable;
			switch(guiID)
			{
			case 0 : //Guns
				playerEntity.openGui(FlansMod.INSTANCE, 6, playerEntity.worldObj, d.chunkCoordX, d.chunkCoordY, d.chunkCoordZ); break;
			case 1 : //Bombs / Shells
				playerEntity.openGui(FlansMod.INSTANCE, 7, playerEntity.worldObj, d.chunkCoordX, d.chunkCoordY, d.chunkCoordZ); break;
			case 2 : //Fuel
				playerEntity.openGui(FlansMod.INSTANCE, 8, playerEntity.worldObj, d.chunkCoordX, d.chunkCoordY, d.chunkCoordZ); break;
			case 3 : //Cargo
				playerEntity.openGui(FlansMod.INSTANCE, 9, playerEntity.worldObj, d.chunkCoordX, d.chunkCoordY, d.chunkCoordZ); break;
			case 4 : //Mecha
				playerEntity.openGui(FlansMod.INSTANCE, 10, playerEntity.worldObj, d.chunkCoordX, d.chunkCoordY, d.chunkCoordZ); break;
			}
		}
	}

	@Override
	public void handleClientSide(EntityPlayer clientPlayer) 
	{
		FlansMod.log("Received GUI open packet on client. Skipping.");
	}
}

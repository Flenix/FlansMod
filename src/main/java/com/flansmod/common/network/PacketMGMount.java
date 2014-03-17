package com.flansmod.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;

import com.flansmod.common.FlansMod;
import com.flansmod.common.guns.EntityMG;

import cpw.mods.fml.relauncher.Side;

public class PacketMGMount extends PacketBase 
{
	public int playerEntityId;
	public int mgEntityId;
	public boolean mounting;

	public PacketMGMount() {}

	public PacketMGMount(EntityPlayer player, EntityMG mg, boolean mounting)
	{
		playerEntityId = player.getEntityId();
		mgEntityId = mg.getEntityId();
		this.mounting = mounting;
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) 
	{
		data.writeInt(playerEntityId);
    	data.writeInt(mgEntityId);
    	data.writeBoolean(mounting);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) 
	{
		playerEntityId = data.readInt();
		mgEntityId = data.readInt();
		mounting = data.readBoolean();
	}

	@Override
	public void handleServerSide(EntityPlayerMP playerEntity) 
	{
		FlansMod.log("Received MG mount packet on server. Skipping.");
	}

	@Override
	public void handleClientSide(EntityPlayer clientPlayer) 
	{
		EntityPlayer player = (EntityPlayer)clientPlayer.worldObj.getEntityByID(playerEntityId);
		EntityMG mg = (EntityMG)clientPlayer.worldObj.getEntityByID(mgEntityId);
		if(mg != null && player != null)
			mg.mountGun(player, mounting);
	}
}

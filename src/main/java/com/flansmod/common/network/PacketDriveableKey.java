package com.flansmod.common.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import com.flansmod.api.IControllable;
import com.flansmod.common.FlansMod;

import cpw.mods.fml.relauncher.Side;

public class PacketDriveableKey extends PacketBase 
{
	public int key;
	
	public PacketDriveableKey() {}

	public PacketDriveableKey(int k)
	{
		key = k;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf data) 
	{
		data.writeInt(key);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf data) 
	{
		key = data.readInt();
	}

	@Override
	public void handleServerSide(EntityPlayerMP playerEntity) 
	{
		if(playerEntity.ridingEntity != null && playerEntity.ridingEntity instanceof IControllable)
		{
			((IControllable)playerEntity.ridingEntity).pressKey(key, playerEntity);
		}
	}

	@Override
	public void handleClientSide(EntityPlayer clientPlayer) 
	{
		FlansMod.log("Driveable keypress packet received on client. Skipping.");
	}

}

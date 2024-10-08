package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2_18;

import protocolsupport.protocol.codec.VarNumberCodec;
import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleWorldBorderInit;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;

public class WorldBorderInit extends MiddleWorldBorderInit implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public WorldBorderInit(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData worldborderPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_WORLDBORDER_INIT);
		worldborderPacket.writeDouble(x);
		worldborderPacket.writeDouble(z);
		worldborderPacket.writeDouble(oldSize);
		worldborderPacket.writeDouble(newSize);
		VarNumberCodec.writeVarLong(worldborderPacket, speed);
		VarNumberCodec.writeVarInt(worldborderPacket, teleportBound);
		VarNumberCodec.writeVarInt(worldborderPacket, warnDelay);
		VarNumberCodec.writeVarInt(worldborderPacket, warnDistance);
		io.writeClientbound(worldborderPacket);
	}

}

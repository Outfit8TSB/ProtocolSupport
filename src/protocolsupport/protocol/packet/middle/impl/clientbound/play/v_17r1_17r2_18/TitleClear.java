package protocolsupport.protocol.packet.middle.impl.clientbound.play.v_17r1_17r2_18;

import protocolsupport.protocol.packet.ClientBoundPacketData;
import protocolsupport.protocol.packet.ClientBoundPacketType;
import protocolsupport.protocol.packet.middle.base.clientbound.play.MiddleTitleClear;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r1;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV17r2;
import protocolsupport.protocol.packet.middle.impl.clientbound.IClientboundMiddlePacketV18;

public class TitleClear extends MiddleTitleClear implements
IClientboundMiddlePacketV17r1,
IClientboundMiddlePacketV17r2,
IClientboundMiddlePacketV18 {

	public TitleClear(IMiddlePacketInit init) {
		super(init);
	}

	@Override
	protected void write() {
		ClientBoundPacketData titleclearPacket = ClientBoundPacketData.create(ClientBoundPacketType.PLAY_TITLE_CLEAR);
		titleclearPacket.writeBoolean(reset);
		io.writeClientbound(titleclearPacket);
	}

}

import ch.tron.middleman.ToGameMessageService;
import ch.tron.middleman.ToTransportMessageService;

module ch.tron.middleman {

    requires json;

    exports ch.tron.middleman;
    exports ch.tron.middleman.messagedto;
    exports ch.tron.middleman.messagedto.transporttogame;
    exports ch.tron.middleman.messagedto.gametotransport;
    exports ch.tron.middleman.messagehandler;

    uses ToGameMessageService;
    uses ToTransportMessageService;
}
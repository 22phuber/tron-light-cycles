import ch.tron.middleman.services.ToGameMessageService;
import ch.tron.middleman.services.ToTransportMessageService;

module ch.tron.middleman {

    requires json;

    exports ch.tron.middleman.messagedto;
    exports ch.tron.middleman.messagedto.backAndForth;
    exports ch.tron.middleman.messagedto.transporttogame;
    exports ch.tron.middleman.messagedto.gametotransport;
    exports ch.tron.middleman.messagehandler;
    exports ch.tron.middleman.services;

    uses ToGameMessageService;
    uses ToTransportMessageService;
}
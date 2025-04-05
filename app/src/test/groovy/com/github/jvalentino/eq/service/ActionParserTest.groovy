package com.github.jvalentino.eq.service

import spock.lang.Specification
import spock.lang.Unroll

class ActionParserTest extends Specification {

    def setup() {

    }

    @Unroll
    def "test parsing #input"() {

        when:
        LogEvent result = ActionParser.parse(input)

        then:
        result.getActionType() == actionType
        result.getTimestampString() == timestamp
        result.getActor() == actor
        result.getRecipient() == recipient
        result.getDamage() == damage
        result.getActionSubType() == subType

        where:
        input  << [
                "[Sun Mar 30 18:52:48 2025] Your Zealot's Spiked Bracer (Legendary) pulses with light as your vision sharpens.\n",
                "[Sun Mar 30 18:54:35 2025] You slash Qua Senshali Xakra for 365 points of damage.\n",
                "[Sun Mar 30 18:52:51 2025] Trombonius has shielded himself from 69 points of damage. (Nillipus' March of the Wee)\n",
        ]
        // OUTPUT
        actionType << [
                ActionType.NONE,
                ActionType.MELEE,
                ActionType.NONE,
        ]
        timestamp << [
                null,
                "Sun Mar 30 18:54:35 2025",
                null,
        ]
        actor << [
                null,
                "You",
                null,
        ]
        recipient << [
                null,
                "Qua Senshali Xakra",
                null,
        ]
        damage << [
                null,
                365,
                null,
        ]
        subType << [
                null,
                "slash",
                null,
        ]
    }

}

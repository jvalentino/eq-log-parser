package com.github.jvalentino.eq.service

import spock.lang.Specification
import spock.lang.Unroll

class ActionParserTest extends Specification {

    def setup() {

    }

    @Unroll
    def "test parsing #scenario using #input"() {

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
        scenario << [
                "01 Random spell effect",
                "02 Owner melee damage",
                "03 Owner spell shield himself",
                "04 Spell Damage",
        ]
        input  << [
                "[Sun Mar 30 18:52:48 2025] Your Zealot's Spiked Bracer (Legendary) pulses with light as your vision sharpens.\n",
                "[Sun Mar 30 18:54:35 2025] You slash Qua Senshali Xakra for 365 points of damage.\n",
                "[Sun Mar 30 18:52:51 2025] Trombonius has shielded himself from 69 points of damage. (Nillipus' March of the Wee)\n",
                "[Sun Mar 30 18:54:38 2025] Trombonius hit Qua Senshali Xakra for 4087 points of non-melee damage. (Time Snap)\n",
        ]
        // OUTPUT
        actionType << [
                ActionType.NONE,
                ActionType.MELEE,
                ActionType.NONE,
                ActionType.SPELL,
        ]
        timestamp << [
                null,
                "Sun Mar 30 18:54:35 2025",
                null,
                "Sun Mar 30 18:54:38 2025",
        ]
        actor << [
                null,
                "You",
                null,
                "Trombonius",
        ]
        recipient << [
                null,
                "Qua Senshali Xakra",
                null,
                "Qua Senshali Xakra"
        ]
        damage << [
                null,
                365,
                null,
                4087,
        ]
        subType << [
                null,
                "slash",
                null,
                "Time Snap",
        ]
    }

}

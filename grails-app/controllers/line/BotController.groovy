package line

import static org.springframework.http.HttpStatus.*
import grails.converters.*
import grails.rest.*
import grails.transaction.Transactional
import line.Bot;

class BotController  extends RestfulController {

    LineBotApiService lineBotApiService
    
    static responseFormats = ['json', 'xml']
    BotController() {
        super(Bot)
    }
    
    def index() { }

	/**
	 * expected.
	 * curl -X POST -H "Content-Type:application/json; charset=UTF-8" -d @/Users/mitanishouhei/Desktop/LineJsonSample.json http://localhost:8080/line/bot
	 *{"result":[{"from":"u206d25c2ea6bd87c17655609a1c37cb8","fromChannel":"1341301815","to":["u0cc15697597f61dd8b01cea8b027050e"],"toChannel":"1441301333","eventType":"138311609000106303","id":"ABCDEF-12345678901","content":{}}]}
	 *
	 */
    @Transactional
    def save(Bot bot) {
        if (bot == null) {
            transactionStatus.setRollbackOnly()
            render status: NOT_FOUND
            return
        }

        if (bot.hasErrors()) {
            transactionStatus.setRollbackOnly()
            println bot.errors
            //respond bot.errors, view:'create'
            return
        }
        
        def reqMap = bot.getResult().get(0).get(0)
        println reqMap
        
        lineBotApiService.repeat(reqMap)
        
        render status :OK
    }
}
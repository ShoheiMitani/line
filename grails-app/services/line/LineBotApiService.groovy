package line

import grails.transaction.Transactional
import groovyx.net.http.HTTPBuilder
import static groovyx.net.http.ContentType.JSON

@Transactional

class LineBotApiService {
    def repeat(reqMap){
        def to = reqMap.get('content').get('from')
        def text = reqMap.get('content').get('text')
        
        sendTextMessage(to,text)
    }
    
    def sendTextMessage(to,text){
        def http = new HTTPBuilder( 'https://trialbot-api.line.me/v1/events' )
        http.post( headers : ['Content-type': 'application/json; charset=UTF-8',
                               'X-Line-ChannelID':'1466137400', //Channel ID
                               'X-Line-ChannelSecret':'acd1150a257fee513e487da7d6ee5eaf', // Channel secre
                               'X-Line-Trusted-User-With-ACL':' u99c2c232b6c8a2a4dc2177e6834be630' // MID (of Channel)
                            ],
                   body: [to: [to],
                          toChannel: 1383378250, // Fixed value
                          eventType: '138311608800106203', //Fixed value,
                          content: [contentType: '1', //Text message
                                    toType: '1',  //Type of recipient set in the to property. (1 = user)
                                    text:text
                                  ]
                          ]) { resp,reader  ->
                 
            if (resp.status == 200) {
            }
            else if (resp.status == 404) {
                println "Access denied"
            }
            else {
                println "Unexpected failure: ${resp.statusLine}"
            }
        }
    }
}

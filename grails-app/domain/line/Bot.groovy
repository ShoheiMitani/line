package line

import grails.rest.*

@Resource(uri='/line/bot', readOnly = false, formats = ['json', 'xml'])
class Bot {
    ArrayList result
}

package live.lafi.util.public_model

object ContentManager {
    /**
     * API로 요청한 Content의 contentSrl, Content(String)을 보관하는 변수이다.
     */
    private val requestContentList = ArrayList<Pair<Long, String>>()

    private var isContentServiceRunning = false

    /**
     * 서버에 요청한 contentSrl이 존재하는지 확인하여 true, false로 반환한다.
     */
    fun isExistRequestContentSrl(contentSrl: Long): Boolean {
        requestContentList.find { it.first == contentSrl }?.let { return true }

        return false
    }

    fun addRequestContent(contentSrl: Long) {
        requestContentList.add(Pair(contentSrl, ""))
    }

    fun addRequestContent(contentSrl: Long, content: String) {
        requestContentList.add(Pair(contentSrl, content))
    }

    fun isContentServiceRunning() = isContentServiceRunning

    fun setContentServiceRunning(isRunning: Boolean) {
        isContentServiceRunning = isRunning
    }
}
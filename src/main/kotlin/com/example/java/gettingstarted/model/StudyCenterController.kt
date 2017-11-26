package com.example.java.gettingstarted.model

import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


interface StudyCenterService {
    fun findAllSufficiency(page: Int, length: Int): List<StudyCenter>
    fun findAllRoyal(page: Int, length: Int): List<StudyCenter>
    fun findAllOther(page: Int, length: Int): List<StudyCenter>
    fun findOne(id: Long): StudyCenter?
    fun findAllIsoc(page: Int, length: Int): List<StudyCenter>

}

@Service
class StudyCenterServiceImpl : StudyCenterService {
    override fun findAllIsoc(page: Int, length: Int): List<StudyCenter> {
        val forObject = restTemplate.getForObject(
                "$BASE_URL/api/search?page={page}&limit={limit}&type={type}&query={query}",
                Wrapper::class.java,
                page,
                length,
                2,
                "organization=\"กอ.รมน.กศน.\""
        )
        return convertOther(forObject.knowledgeOther)
    }

    override fun findOne(id: Long): StudyCenter? {

        val forObject = restTemplate.getForObject("$BASE_URL/api/search?page={page}&limit={limit}&id={id}", Wrapper::class.java, 0, 1, id)
        if (forObject.knowledgeEconomy.isNotEmpty()) return convertSufficiency(forObject.knowledgeEconomy).firstOrNull()
        else if (forObject.knowledgeDevelopment.isNotEmpty()) return convertRoyalty(forObject.knowledgeDevelopment).firstOrNull()
        else if (forObject.knowledgeOther.isNotEmpty()) return convertOther(forObject.knowledgeOther).firstOrNull()
        return null

    }

    val BASE_URL = "https://rdpb-2.appspot.com"
    val restTemplate = RestTemplate()
    override fun findAllRoyal(i: Int, i1: Int): List<StudyCenter> {
        val forObject = restTemplate.getForObject("$BASE_URL/api/search?page={page}&limit={limit}&type={type}", Wrapper::class.java, i, i1, 1)
        return convertRoyalty(forObject.knowledgeDevelopment)
    }

    override fun findAllSufficiency(offset: Int, length: Int): List<StudyCenter> {
        val forObject = restTemplate.getForObject("$BASE_URL/api/search?page={page}&limit={limit}&type={type}", Wrapper::class.java, offset, length, 0)
        return convertSufficiency(forObject.knowledgeEconomy)
    }

    override fun findAllOther(i: Int, i1: Int): List<StudyCenter> {
        val forObject = restTemplate.getForObject(
                "$BASE_URL/api/search?page={page}&limit={limit}&type={type}&query={query}",
                Wrapper::class.java,
                i,
                i1,
                2,
                "NOT organization:\"กอ.รมน.กศน.\""
        )
        return convertOther(forObject.knowledgeOther)
    }

    private fun convertSufficiency(knowledgeEconomy: List<SufficiencyStudyCenterSource>): List<StudyCenter> {
        return knowledgeEconomy.map {
            val location: String = if (it.location?.lat != null && it.location?.lng != null) it.location?.lat.toString() + "," + it.location?.lng else "ไม่มีข้อมูล"

            StudyCenter(id = it.id!!.toLong(), name = it.name!!, detail = it.detail!!, address = it.address!!,
                    type = it.type!!, mainType = "ศูนย์เรียนรู้เศรษฐกิจพอเพียง", year = it.year ?: 0, status = getStatus(it.status),
                    contact = it.coordinator ?: "ไม่ระบุ", location = location, complete = getComplete(it.complete),
                    award = it.reward ?: "ไม่มี",
                    url = getUrl(it.id!!))
        }
    }

    fun getComplete(complete: Boolean?): String = when (complete) {
        true -> "ครบถ้วน"
        else -> "ไม่ครบถ้วน"
    }

    private fun getStatus(status: Boolean?): String = when (status) {
        true -> "ศูนย์เรียนรู้"
        else -> "พื้นที่เป้าหมาย"
    }

    private fun convertRoyalty(knowledgeDevelopment: List<RoyalStudyCenterSource>): List<StudyCenter> {

        return knowledgeDevelopment.map {
            val location: String = if (it.location?.lat != null && it.location?.lng != null) it.location?.lat.toString() + "," + it.location?.lng else "ไม่มีข้อมูล"
            StudyCenter(id = it.id!!.toLong(), name = it.name!!, url = getUrl(it.id!!), address = it.address!!,
                    type = it.type!!, mainType = "ศูนย์เรียนรู้ตามแนวพระราชดำริฯ", location = location, year = it.year ?: 0, complete = getComplete(it.complete),
                    organization = it.area!!,
                    status = getStatus(it.status),
                    detail = it.detail!!)
        }
    }

    private fun convertOther(knowledgeOther: List<StudyCenterOtherSource>): List<StudyCenter> {
        return knowledgeOther.map {
            val location: String = if (it.location?.lat != null && it.location?.lng != null) it.location?.lat.toString() + "," + it.location?.lng else "ไม่มีข้อมูล"
            StudyCenter(id = it.id!!.toLong(), name = it.name!!, url = getUrl(it.id!!), address = it.address!!,
                    type = it.type!!, mainType = "ศูนย์เรียนรู้อื่นๆ", organization = it.division ?: "", externalId = it.externalId, contact = it.coordinator ?: "",
                    complete = getComplete(it.complete), status = getStatus(it.status), year = it.year ?: 0, award = it.reward ?: "",
                    coordinator = it.contact, location = location,
                    detail = it.detail!!)
        }
    }

    fun getUrl(id: String): String {
        return "/studyCenter/$id"
    }

}


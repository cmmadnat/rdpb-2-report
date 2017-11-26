package com.example.web

import com.example.model.*
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
            StudyCenter(id = it.id!!.toLong(), name = it.name!!, detail = it.detail!!, address = it.address!!,
                    type = it.type!!, mainType = "ศูนย์เรียนรู้เศรษฐกิจพอเพียง",
                    url = getUrl(it.id!!))
        }
    }

    private fun convertRoyalty(knowledgeDevelopment: List<RoyalStudyCenterSource>): List<StudyCenter> {

        return knowledgeDevelopment.map {
            StudyCenter(id = it.id!!.toLong(), name = it.name!!, detail = it.detail!!, address = it.address!!,
                    type = it.type!!, mainType = "ศูนย์เรียนรู้ตามแนวพระราชดำริฯ",
                    url = getUrl(it.id!!))
        }
    }

    private fun convertOther(knowledgeOther: List<StudyCenterOtherSource>): List<StudyCenter> {
        return knowledgeOther.map {
            StudyCenter(id = it.id!!.toLong(), name = it.name!!, detail = it.detail!!, address = it.address!!,
                    type = it.type!!, mainType = "ศูนย์เรียนรู้อื่นๆ",
                    url = getUrl(it.id!!))
        }
    }

    fun getUrl(id: String): String {
        return "/studyCenter/$id"
    }

}


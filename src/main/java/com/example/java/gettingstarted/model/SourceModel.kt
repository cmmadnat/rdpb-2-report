package com.example.java.gettingstarted.model

class File {

    var fileName: String? = null
    var key: String? = null
    var logo: String? = null
    var color: String? = null
    var filePath: String? = null

}

class Location {

    var lat: Double? = null
    var lng: Double? = null

}

class Picture {

    var serveURL: String? = null
    var key: String? = null
    var caption: String? = null

}

class Locationother {

    var lat: Float? = null
    var lng: Float? = null

}

class SufficiencyStudyCenterSource {

    var id: String? = null
    var name: String? = null
    var type: String? = null
    var year: Int? = null
    var address: String? = null
    var picture: String? = null
    var coordinator: String? = null
    var location: Location? = null
    var namegroup: String? = null
    var reward: String? = null
    var detail: String? = null
    var tel: String? = null
    var email: String? = null
    var gchat: String? = null
    var lineid: String? = null
    var icon: String? = null
    var pictures: List<Picture>? = null
    var files: List<File>? = null
    var status: Boolean? = null
    var province: String? = null
    var complete: Boolean? = null
}

class RoyalStudyCenterSource {

    var id: String? = null
    var area: String? = null
    var name: String? = null
    var type: String? = null
    var address: String? = null
    var picture: String? = null
    var location: Location? = null
    var addressarea: String? = null
    var namegroup: String? = null
    var detail: String? = null
    var tel: String? = null
    var email: String? = null
    var gchat: String? = null
    var lineid: String? = null
    var icon: String? = null
    var pictures: List<Picture>? = null
    var files: List<File>? = null
    var coordinator: String? = null
    var year: Int? = null
    var status: Boolean? = null
    var province: String? = null

    var complete: Boolean? = null
}

class StudyCenterOtherSource {

    var id: String? = null
    var division: String? = null
    var name: String? = null
    var type: String? = null
    var year: Int? = null
    var address: String? = null
    var citizenid: String? = null
    var picture: String? = null
    var location: Location? = null
    var locationother: Locationother? = null
    var responsible: String? = null
    var namegroup: String? = null
    var reward: String? = null
    var detail: String? = null
    var tel: String? = null
    var email: String? = null
    var gchat: String? = null
    var lineid: String? = null
    var icon: String? = null
    var pictures: List<Picture>? = null
    var files: List<Any>? = null
    var coordinator: String? = null
    var status: Boolean? = null
    var province: String? = null

    var complete: Boolean? = null
}

data class Wrapper(var knowledgeEconomy: List<SufficiencyStudyCenterSource> = listOf()) {
    val knowledgeDevelopment: List<RoyalStudyCenterSource> = listOf()
    val knowledgeOther: List<StudyCenterOtherSource> = listOf()
}
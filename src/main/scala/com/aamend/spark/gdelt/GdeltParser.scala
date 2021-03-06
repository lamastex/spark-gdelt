package com.aamend.spark.gdelt

import java.sql.{Date, Timestamp}
import java.text.SimpleDateFormat

import scala.util.{Try,Success,Failure}

import java.security.MessageDigest
import java.util.Base64

/**
  * Created by antoine on 24/03/2018.
  */
object GdeltParser {

  private val DELIMITER = "\t"

  // Perform SHA-256 hashing to get a digest of the input string
  def sha_256(in: String): String = {
    // Instantiate MD with algo SHA-256
    val md: MessageDigest = MessageDigest.getInstance("SHA-256")  
    // Encode the resulting byte array as a base64 string
    new String(Base64.getEncoder.encode(md.digest(in.getBytes)),"UTF-8") 
  }

  def parseEventV1(str: String): EventV1 = {
    
    val tokens = str.split(DELIMITER)

    val actor1Code: Actor = Actor(
      cameoRaw = T(()=>tokens(5)),
      cameoName = T(()=>tokens(6)),
      cameoCountryCode = T(()=>tokens(7)),
      cameoGroupCode = T(()=>tokens(8)),
      cameoEthnicCode = T(()=>tokens(9)),
      cameoReligion1Code = T(()=>tokens(10)),
      cameoReligion2Code = T(()=>tokens(11)),
      cameoType1Code = T(()=>tokens(12)),
      cameoType2Code = T(()=>tokens(13)),
      cameoType3Code = T(()=>tokens(14))
    )

    val actor2Code: Actor = Actor(
      cameoRaw = T(()=>tokens(15)),
      cameoName = T(()=>tokens(16)),
      cameoCountryCode = T(()=>tokens(17)),
      cameoGroupCode = T(()=>tokens(18)),
      cameoEthnicCode = T(()=>tokens(19)),
      cameoReligion1Code = T(()=>tokens(20)),
      cameoReligion2Code = T(()=>tokens(21)),
      cameoType1Code = T(()=>tokens(22)),
      cameoType2Code = T(()=>tokens(23)),
      cameoType3Code = T(()=>tokens(24))
    )

    val actor1GeoPoint: GeoPoint = GeoPoint(T(()=>tokens(39).toFloat), T(()=>tokens(40).toFloat))
    val actor2GeoPoint: GeoPoint = GeoPoint(T(()=>tokens(46).toFloat), T(()=>tokens(47).toFloat))
    val eventGeoPoint: GeoPoint = GeoPoint(T(()=>tokens(53).toFloat), T(()=>tokens(54).toFloat))

    val actor1Geo: Location = Location(
      geoType = T(()=>geoType(tokens(35).toInt)),
      geoName = T(()=>tokens(36)),
      countryCode = T(()=>tokens(37)),
      adm1Code = T(()=>tokens(38)),
      geoPoint = T(()=>actor1GeoPoint),
      featureId = T(()=>tokens(41))
    )

    val actor2Geo: Location = Location(
      geoType = T(()=>geoType(tokens(42).toInt)),
      geoName = T(()=>tokens(43)),
      countryCode = T(()=>tokens(44)),
      adm1Code = T(()=>tokens(45)),
      geoPoint = Some(actor2GeoPoint),
      featureId = T(()=>tokens(48))
    )

    val eventGeo: Location = Location(
      geoType = T(()=>geoType(tokens(49).toInt)),
      geoName = T(()=>tokens(50)),
      countryCode = T(()=>tokens(51)),
      adm1Code = T(()=>tokens(52)),
      geoPoint = Some(eventGeoPoint),
      featureId = T(()=>tokens(55))
    )

    EventV1(
      eventId = T(()=>tokens(0).toInt),
      eventDay = T(()=>new Date(new SimpleDateFormat("yyyyMMdd").parse(tokens(1)).getTime)),
      actor1Code = Some(actor1Code),
      actor2Code = Some(actor2Code),
      isRoot = T(()=>tokens(25) == "1"),
      cameoEventCode = T(()=>tokens(26)),
      cameoEventBaseCode = T(()=>tokens(27)),
      cameoEventRootCode = T(()=>tokens(28)),
      quadClass = T(()=>quadClass(tokens(29).toInt)),
      goldstein = T(()=>tokens(30).toFloat),
      numMentions = T(()=>tokens(31).toInt),
      numSources = T(()=>tokens(32).toInt),
      numArticles = T(()=>tokens(33).toInt),
      avgTone = T(()=>tokens(34).toFloat),
      actor1Geo = Some(actor1Geo),
      actor2Geo = Some(actor2Geo),
      eventGeo = Some(eventGeo),
      dateAdded = T(()=>new Date(new SimpleDateFormat("yyyyMMdd").parse(tokens(56)).getTime)),
      sourceUrl = T(()=>tokens(57)),
      hash = T(()=>sha_256(str)),
      errors = T(()=>"")
    )
  }

  def parseEventV2(str: String): EventV2 = {

    val tokens = str.split(DELIMITER)

    val actor1Code: Actor = Actor(
      cameoRaw = T(()=>tokens(5)),
      cameoName = T(()=>tokens(6)),
      cameoCountryCode = T(()=>tokens(7)),
      cameoGroupCode = T(()=>tokens(8)),
      cameoEthnicCode = T(()=>tokens(9)),
      cameoReligion1Code = T(()=>tokens(10)),
      cameoReligion2Code = T(()=>tokens(11)),
      cameoType1Code = T(()=>tokens(12)),
      cameoType2Code = T(()=>tokens(13)),
      cameoType3Code = T(()=>tokens(14))
    )

    val actor2Code: Actor = Actor(
      cameoRaw = T(()=>tokens(15)),
      cameoName = T(()=>tokens(16)),
      cameoCountryCode = T(()=>tokens(17)),
      cameoGroupCode = T(()=>tokens(18)),
      cameoEthnicCode = T(()=>tokens(19)),
      cameoReligion1Code = T(()=>tokens(20)),
      cameoReligion2Code = T(()=>tokens(21)),
      cameoType1Code = T(()=>tokens(22)),
      cameoType2Code = T(()=>tokens(23)),
      cameoType3Code = T(()=>tokens(24))
    )

    val actor1GeoPoint: GeoPoint = GeoPoint(T(()=>tokens(40).toFloat), T(()=>tokens(41).toFloat))
    val actor2GeoPoint: GeoPoint = GeoPoint(T(()=>tokens(48).toFloat), T(()=>tokens(49).toFloat))
    val eventGeoPoint: GeoPoint = GeoPoint(T(()=>tokens(56).toFloat), T(()=>tokens(57).toFloat))

    val actor1Geo: Location = Location(
      geoType = T(()=>geoType(tokens(35).toInt)),
      geoName = T(()=>tokens(36)),
      countryCode = T(()=>tokens(37)),
      adm1Code = T(()=>tokens(38)),
      adm2Code = T(()=>tokens(39)),
      geoPoint = T(()=>actor1GeoPoint),
      featureId = T(()=>tokens(42))
    )

    val actor2Geo: Location = Location(
      geoType = T(()=>geoType(tokens(43).toInt)),
      geoName = T(()=>tokens(44)),
      countryCode = T(()=>tokens(45)),
      adm1Code = T(()=>tokens(46)),
      adm2Code = T(()=>tokens(47)),
      geoPoint = Some(actor2GeoPoint),
      featureId = T(()=>tokens(50))
    )

    val eventGeo: Location = Location(
      geoType = T(()=>geoType(tokens(51).toInt)),
      geoName = T(()=>tokens(52)),
      countryCode = T(()=>tokens(53)),
      adm1Code = T(()=>tokens(54)),
      adm2Code = T(()=>tokens(55)),
      geoPoint = Some(eventGeoPoint),
      featureId = T(()=>tokens(58))
    )

    EventV2(
      eventId = T(()=>tokens(0).toInt),
      eventDay = T(()=>new Date(new SimpleDateFormat("yyyyMMdd").parse(tokens(1)).getTime)),
      actor1Code = Some(actor1Code),
      actor2Code = Some(actor2Code),
      isRoot = T(()=>tokens(25) == "1"),
      cameoEventCode = T(()=>tokens(26)),
      cameoEventBaseCode = T(()=>tokens(27)),
      cameoEventRootCode = T(()=>tokens(28)),
      quadClass = T(()=>quadClass(tokens(29).toInt)),
      goldstein = T(()=>tokens(30).toFloat),
      numMentions = T(()=>tokens(31).toInt),
      numSources = T(()=>tokens(32).toInt),
      numArticles = T(()=>tokens(33).toInt),
      avgTone = T(()=>tokens(34).toFloat),
      actor1Geo = Some(actor1Geo),
      actor2Geo = Some(actor2Geo),
      eventGeo = Some(eventGeo),
      dateAdded = T(()=>new Timestamp(new SimpleDateFormat("yyyyMMddHHmmss").parse(tokens(59)).getTime)),
      sourceUrl = T(()=>tokens(60)),
      hash = T(()=>sha_256(str)),
      errors = T(()=>"")
    )
  }

  def parseNormDaily(str: String): EventNormDaily = {
    val tokens = str.split(",")

    EventNormDaily(
      day = T(() => new Date(new SimpleDateFormat("yyyyMMdd").parse(tokens(0)).getTime)),
      eventCount = T(() => tokens(1).toInt)
    )
  }

  def parseNormDailyByCountry(str: String): EventNormDailyByCountry = {
    val tokens = str.split(",")

    EventNormDailyByCountry(
      day = T(() => new Date(new SimpleDateFormat("yyyyMMdd").parse(tokens(0)).getTime)),
      countryCode = T(() => tokens(1)),
      eventCount = T(() => tokens(2).toInt)
    )
  }

  private def quadClass(quadClass: Int): String = quadClass match {
    case 1 => "VERBAL_COOPERATION"
    case 2 => "MATERIAL_COOPERATION"
    case 3 => "VERBAL_CONFLICT"
    case 4 => "MATERIAL_CONFLICT"
    case _ => "UNKNOWN"
  }

  private def geoType(geoType: Int): String = geoType match {
    case 1 => "COUNTRY"
    case 2 => "USSTATE"
    case 3 => "USCITY"
    case 4 => "WORLDCITY"
    case 5 => "WORLDSTATE"
    case _ => "UNKNOWN"
  }

  def parseMentionV2(str: String): MentionV2 = {

    val tokens = str.split(DELIMITER)

    MentionV2(
      eventId = T(()=>tokens(0).toLong),
      eventTime = T(()=>new Timestamp(new SimpleDateFormat("yyyyMMddHHmmss").parse(tokens(1)).getTime)),
      mentionTime = T(()=>new Timestamp(new SimpleDateFormat("yyyyMMddHHmmss").parse(tokens(2)).getTime)),
      mentionType = T(()=>buildSourceCollectionIdentifier(tokens(3))),
      mentionSourceName = T(()=>tokens(4)),
      mentionIdentifier = T(()=>tokens(5)),
      sentenceId = T(()=>tokens(6).toInt),
      actor1CharOffset = T(()=>tokens(7).toInt),
      actor2CharOffset = T(()=>tokens(8).toInt),
      actionCharOffset = T(()=>tokens(9).toInt),
      inRawText = T(()=>tokens(10).toInt),
      confidence = T(()=>tokens(11).toInt),
      mentionDocLen = T(()=>tokens(12).toInt),
      mentionDocTone = T(()=>tokens(13).toFloat),
      hash = T(()=>sha_256(str)),
      errors = T(()=>"")
    )
    
  }

  private def sourceCollectionIdentifier(sourceCollectionIdentifier: Int): String = sourceCollectionIdentifier match {
    case 1 => "WEB"
    case 2 => "CITATION_ONLY"
    case 3 => "CORE"
    case 4 => "DTIC"
    case 5 => "JSTOR"
    case 6 => "NON_TEXTUAL_SOURCE"
    case _ => "UNKNOWN"
  }

  def parseGkgV2(str: String): GKGEventV2 = {
    T(()=>
      {
        val values = str.split(DELIMITER, -1)

        GKGEventV2(
          gkgRecordId = buildGkgRecordId(values(0)),
          publishDate = buildPublishDate(values(1)),
          sourceCollectionIdentifier = T(()=>buildSourceCollectionIdentifier(values(2))),
          sourceCommonName = T(()=>values(3)),
          documentIdentifier = T(()=>values(4)),
          counts = T(()=>buildCounts(values(5))).getOrElse(List.empty[Count]),
          enhancedCounts = T(()=>buildEnhancedCounts(values(6))).getOrElse(List.empty[EnhancedCount]),
          themes = T(()=>buildThemes(values(7))).getOrElse(List.empty[String]),
          enhancedThemes = T(()=>buildEnhancedThemes(values(8))).getOrElse(List.empty[EnhancedTheme]),
          locations = T(()=>buildLocations(values(9))).getOrElse(List.empty[Location]),
          enhancedLocations = T(()=>buildEnhancedLocations(values(10))).getOrElse(List.empty[EnhancedLocation]),
          persons = T(()=>buildPersons(values(11))).getOrElse(List.empty[String]),
          enhancedPersons = T(()=>buildEnhancedPersons(values(12))).getOrElse(List.empty[EnhancedPerson]),
          organisations = T(()=>buildOrganisations(values(13))).getOrElse(List.empty[String]),
          enhancedOrganisations = T(()=>buildEnhancedOrganisations(values(14))).getOrElse(List.empty[EnhancedOrganisation]),
          tone = T(()=>buildTone(values(15))),
          enhancedDates = T(()=>buildEnhancedDates(values(16))).getOrElse(List.empty[EnhancedDate]),
          gcams = T(()=>buildGcams(values(17))).getOrElse(List.empty[Gcam]),
          sharingImage = T(()=>values(18)),
          relatedImages = T(()=>buildRelatedImages(values(19))).getOrElse(List.empty[String]),
          socialImageEmbeds = T(()=>buildSocialImageEmbeds(values(20))).getOrElse(List.empty[String]),
          socialVideoEmbeds = T(()=>buildSocialVideoEmbeds(values(21))).getOrElse(List.empty[String]),
          quotations = T(()=>buildQuotations(values(22))).getOrElse(List.empty[Quotation]),
          allNames = T(()=>buildNames(values(23))).getOrElse(List.empty[Name]),
          amounts = T(()=>buildAmounts(values(24))).getOrElse(List.empty[Amount]),
          translationInfo = T(()=>buildTranslationInfo(values(25))),
          extrasXML = T(()=>values(26)),
          hash = T(()=>sha_256(str)),
          errors = T(()=>"")
         )
      }
     ).getOrElse(GKGEventV2())
  }

  def parseGkgV1(str: String): GKGEventV1 = {
    T(() => 
      {
        val values = str.split(DELIMITER, -1)

        GKGEventV1(
          publishDate = buildPublishDateV1(values(0)),
          numArticles = T(() => values(1).toInt),
          counts = T(()=>buildCounts(values(2))).getOrElse(List.empty[Count]),
          themes = T(()=>buildThemes(values(3))).getOrElse(List.empty[String]),
          locations = T(()=>buildLocations(values(4))).getOrElse(List.empty[Location]),
          persons = T(()=>buildPersons(values(5))).getOrElse(List.empty[String]),
          organisations = T(()=>buildOrganisations(values(6))).getOrElse(List.empty[String]),
          tone = T(()=>buildToneV1(values(7))),
          eventIds = T(() => buildEventIdsV1(values(8))).getOrElse(List.empty[Int]),
          sources = T(() => buildSourcesV1(values(9))).getOrElse(List.empty[String]),
          sourceUrls = T(() => buildSourceUrlsV1(values(10))).getOrElse(List.empty[String]),
          hash = T(() => sha_256(str)),
          errors = T(()=>"")
        )
      }
    ).getOrElse(GKGEventV1())
  }
 
  def parseGkgCountV1(str: String): GKGCountV1 = {
    T(() =>
    {
        val values = str.split(DELIMITER, -1)
        val gkgCountGeoPoint = GeoPoint(
                                        latitude = T(()=>values(9).toFloat),
                                        longitude = T(()=>values(10).toFloat)
                                        )
        val gkgCountLocation = Location(
                                        geoType = T(()=>geoType(values(5).toInt)),
                                        geoName = T(() => values(6)),
                                        countryCode = T(() => values(7)),
                                        adm1Code = T(() => values(8)),
                                        geoPoint = Some(gkgCountGeoPoint),
                                        featureId = T(() => values(11))
                                        )
        val gkgCountCounts = Count(
                                    countType = T(() => values(2)),
                                    count = T(() => values(3).toInt),
                                    objectType = T(() => values(4)),
                                    location = Some(gkgCountLocation)
                                    )
        GKGCountV1(
          publishDate = buildPublishDateV1(values(0)),
          numArticles = T(() => values(1).toInt),
          counts = Some(gkgCountCounts),
          eventIds = T(() => buildEventIdsV1(values(12))).getOrElse(List.empty[Int]),
          sources = T(() => buildSourcesV1(values(13))).getOrElse(List.empty[String]),
          sourceUrls = T(() => buildSourceUrlsV1(values(14))).getOrElse(List.empty[String])
        )
      }
    ).getOrElse(GKGCountV1())    
  } 

  private def buildPublishDate(str: String): Option[Timestamp] = {
    T(()=>new Timestamp(new SimpleDateFormat("yyyyMMddHHmmSS").parse(str).getTime))
  }

  private def buildPublishDateV1(str: String): Option[Timestamp] = {
    T(()=>new Timestamp(new SimpleDateFormat("yyyyMMdd").parse(str).getTime))
  }

  private def buildGkgRecordId(str: String): Option[GkgRecordId] = {
    T(() => {
      val split = str.split("-")
      val isTranslingual = T(()=>split(1).contains("T"))
      val numberInBatch = T(()=>if (isTranslingual.isDefined && isTranslingual.get) split(1).replace("T", "").toInt else split(1).toInt)
      val publishDate = T(()=>new Timestamp(new SimpleDateFormat("yyyyMMddHHmmSS").parse(split(0)).getTime))
      GkgRecordId(publishDate = publishDate, translingual = isTranslingual, numberInBatch = numberInBatch)
    })
  }

  private def buildTranslationInfo(str: String): TranslationInfo = {
    val values = str.split(";")
    T(()=>TranslationInfo(SRCLC = T(()=>values(0)), ENG = T(()=>values(1)))).getOrElse(TranslationInfo())
  }

  private def buildAmounts(str: String): List[Amount] = {
    str.split(";").map(buildAmount).filter(_.isDefined).map(_.get).toList
  }

  private def buildAmount(str: String): Option[Amount] = {
    val values = str.split(",")
    T(()=>Amount(amount = T(()=>values(0).toDouble), amountType = T(()=>values(1)), charOffset = T(()=>values(2).toInt)))
  }

  private def buildNames(str: String): List[Name] = {
    str.split(";").map(buildName).filter(_.isDefined).map(_.get).toList
  }

  private def buildName(str: String): Option[Name] = {
    val values = str.split(",")
    T(()=>Name(name = T(()=>values(0)), charOffset = T(()=>values(1).toInt)))
  }

  private def buildQuotations(str: String): List[Quotation] = {
    str.split("#").map(buildQuotation).filter(_.isDefined).map(_.get).toList
  }

  private def buildQuotation(str: String): Option[Quotation] = {
    val values = str.split("\\|")
    T(()=>Quotation(charOffset = T(()=>values(0).toInt), charLength = T(()=>values(1).toInt), verb = T(()=>values(2)), quote = T(()=>values(3))))
  }

  private def buildSocialImageEmbeds(str: String): List[String] = str.split(";").toList

  private def buildSocialVideoEmbeds(str: String): List[String] = str.split(";").toList

  private def buildRelatedImages(str: String): List[String] = str.split(";").toList

  private def buildGcams(str: String): List[Gcam] = {
    str.split(",").map(buildGcam).filter(_.isDefined).map(_.get).toList
  }

  private def buildGcam(str: String): Option[Gcam] = {
    val split = str.split(":")
    T(()=>Gcam(gcamCode = T(()=>split(0)), gcamValue = T(()=>split(1).toDouble)))
  }

  private def buildEnhancedDates(str: String): List[EnhancedDate] = {
    str.split(";").map(buildEnhancedDate).filter(_.isDefined).map(_.get).toList
  }

  private def buildEnhancedDate(str: String): Option[EnhancedDate] = {
    val values = str.split("#")
    T(()=>EnhancedDate(dateResolution = T(()=>values(0).toInt), month = T(()=>values(1).toInt), day = T(()=>values(2).toInt), year = T(()=>values(3).toInt), charOffset = T(()=>values(4).toInt)))
  }

  private def buildTone(str: String): Tone = {
    val values = str.split(",")
    T(()=>Tone(tone = T(()=>values(0).toFloat), positiveScore = T(()=>values(1).toFloat), negativeScore = T(()=>values(2).toFloat), polarity = T(()=>values(3).toFloat), activityReferenceDensity = T(()=>values(4).toFloat), selfGroupReferenceDensity = T(()=>values(5).toFloat), wordCount = T(()=>values(6).toInt))).getOrElse(Tone())
  }

  private def buildToneV1(str: String): Tone = {
    val values = str.split(",")
    T(()=>Tone(tone = T(()=>values(0).toFloat),
                 positiveScore = T(()=>values(1).toFloat),
                 negativeScore = T(()=>values(2).toFloat), 
                 polarity = T(()=>values(3).toFloat), 
                 activityReferenceDensity = T(()=>values(4).toFloat), 
                 selfGroupReferenceDensity = T(()=>values(5).toFloat))).getOrElse(Tone())
  }

  private def buildEnhancedOrganisations(str: String): List[EnhancedOrganisation] = {
    str.split(";").map(buildEnhancedOrganisation).filter(_.isDefined).map(_.get).toList
  }

  private def buildEnhancedOrganisation(str: String): Option[EnhancedOrganisation] = {
    val blocks = str.split(",")
    T(()=>EnhancedOrganisation(organisation = T(()=>blocks(0)), charOffset = T(()=>blocks(1).toInt)))
  }

  private def buildOrganisations(str: String) = str.split(";").toList

  private def buildEnhancedPersons(str: String): List[EnhancedPerson] = {
    str.split(";").map(buildEnhancedPerson).filter(_.isDefined).map(_.get).toList
  }

  private def buildEnhancedPerson(str: String): Option[EnhancedPerson] = {
    val blocks = str.split(",")
    T(()=>EnhancedPerson(person = T(()=>blocks(0)), charOffset = T(()=>blocks(1).toInt)))
  }

  private def buildPersons(str: String) = str.split(";").toList

  private def buildSourceCollectionIdentifier(str: String) = T(()=>sourceCollectionIdentifier(str.toInt)).getOrElse(sourceCollectionIdentifier(-1))

  private def buildEnhancedLocations(str: String): List[EnhancedLocation] = {
    str.split(";").map(buildEnhancedLocation).filter(_.isDefined).map(_.get).toList
  }

  private def buildEnhancedLocation(str: String): Option[EnhancedLocation] = {
    val blocks = str.split("#")
    T {() =>
      val geoPoint = GeoPoint(latitude = T(()=>blocks(5).toFloat), longitude = T(()=>blocks(6).toFloat))
      val location = Location(geoType = T(()=>geoType(blocks(0).toInt)), geoName = T(()=>blocks(1)), countryCode = T(()=>blocks(2)), adm1Code = T(()=>blocks(3)), adm2Code = T(()=>blocks(4)), geoPoint = Some(geoPoint), featureId = T(()=>blocks(7)))
      EnhancedLocation(location = Some(location), charOffset = T(()=>blocks(8).toInt))
    }
  }

  private def buildLocations(str: String): List[Location] = {
    str.split(";").map(buildLocation).filter(_.isDefined).map(_.get).toList
  }

  private def buildLocation(str: String): Option[Location] = {
    val blocks = str.split("#")
    T {() =>
      val geoPoint = GeoPoint(latitude = T(()=>blocks(4).toFloat), longitude = T(()=>blocks(5).toFloat))
      Location(geoType = T(()=>geoType(blocks(0).toInt)), geoName = T(()=>blocks(1)), countryCode = T(()=>blocks(2)), adm1Code = T(()=>blocks(3)), geoPoint = Some(geoPoint), featureId = T(()=>blocks(6)))
    }
  }

  /* private def buildLocationsV1(str: String): List[LocationV1] = {
    str.split(";").map(buildLocationV1).filter(_.isDefined).map(_.get).toList
  }

  private def buildLocationV1(str: String): Option[LocationV1] = {
    val blocks = str.split("#")
    T {() =>
      val geoPoint = GeoPointV1(latitudeV1 = T(()=>blocks(4).toFloat), longitudeV1 = T(()=>blocks(5).toFloat))
      LocationV1(geoTypeV1 = T(()=>geoType(blocks(0).toInt)), geoNameV1 = T(()=>blocks(1)), countryCodeV1 = T(()=>blocks(2)), adm1CodeV1 = T(()=>blocks(3)), geoPointV1 = Some(geoPoint), featureIdV1 = T(()=>blocks(6)))
    }
  } */

  private def buildEnhancedThemes(str: String): List[EnhancedTheme] = {
    str.split(";").map(buildEnhancedTheme).filter(_.isDefined).map(_.get).toList
  }

  private def buildEnhancedTheme(str: String): Option[EnhancedTheme] = {
    val blocks = str.split(",")
    T(()=>EnhancedTheme(theme = T(()=>blocks(0)), charOffset = T(()=>blocks(1).toInt)))
  }

  private def buildThemes(str: String): List[String] = str.split(";").toList

  private def buildEnhancedCounts(str: String): List[EnhancedCount] = {
    str.split(";").map(buildEnhancedCount).filter(_.isDefined).map(_.get).toList
  }

  private def buildEnhancedCount(str: String): Option[EnhancedCount] = {
    T {() =>
      val count = T(()=>buildCount(str).get)
      EnhancedCount(count = count, charOffset = T(()=>str.substring(str.lastIndexOf('#') + 1).toInt))
    }
  }

  private def buildCount(str: String): Option[Count] = {
    val blocks = str.split("#")
    T {() =>
      val geoPoint = GeoPoint(latitude = T(()=>blocks(7).toFloat), longitude = T(()=>blocks(8).toFloat))
      val location = Location(geoType = T(()=>geoType(blocks(3).toInt)), geoName = T(()=>blocks(4)), countryCode = T(()=>blocks(5)), adm1Code = T(()=>blocks(6)), geoPoint = Some(geoPoint), featureId = T(()=>blocks(9)))
      Count(countType = T(()=>blocks(0)), count = T(()=>blocks(1).toLong), objectType = T(()=>blocks(2)), location = Some(location))
    }
  }

  private def buildCounts(str: String): List[Count] = {
    str.split(";").map(buildCount).filter(_.isDefined).map(_.get).toList
  }

  private def buildEventIdsV1(str: String): List[Int] = {
    // def strToInt(s: String): Int = s.toInt
    str.split(",").map(_.toInt).toList
  }

  private def buildSourcesV1(str: String): List[String] = {
    str.split(";").toList
  }

  private def buildSourceUrlsV1(str: String): List[String] = {
    str.split("<UDIV>").toList
  }
}

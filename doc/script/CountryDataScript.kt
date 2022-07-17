import java.util.*

val symbol = cur().split("\n")
    .map {
        val aaa = it.split(",")
        aaa.get(0) to aaa.get(1)
    }.toMap()

val countryCodes = Locale.getAvailableLocales().map {
    it.country
}.filter { it.isNotBlank() }

val langs = Locale.getAvailableLocales().map {
    it.country to it.language
}
    .toMap()

//currdata2().forEach {
//    println("${it.key} to ${it.value},")
//}
currdata().forEach {
    println("${it.key} to ${it.value.toString().replace("[", "listOf(").replace("]", ")")},")
//    println(it.toString().replace("[", "listOf(").replace("]", ")") + ",")
}

fun currdata() = res().split("\n").map {
    val a = it.split("||")
    val code = a.get(0)
    val num = a.get(1)
    val scale = a.get(2)
    val name = a.get(3)
    val country = a.get(4)
    val countries = country.split(",").map {
        val patternCode = "\\((.*?)\\)".toRegex()
        val find = patternCode.find(it)?.value

        val cccode = countryCodes.find { code.startsWith(it) }
        val ccds = find?.replace("(", "")?.replace(")", "") ?: cccode

        ccds?.norm() ?: "".norm()
    }
    Pair(
        code.norm(),
        CurrencyData(
            num = num.toInt(),
            scale = scale.toInt(),
            name = name.norm(),
            countries = countries,
            symbol = symbol.get(code)?.norm() ?: "".norm()
        )
    )
}.toMap()

fun currdata2() = res().split("\n")
    .map {
        val a = it.split("||")
        val code = a.get(0)
        val country = a.get(4)
        val countries = country.split(",").map {
            val patternCode = "\\((.*?)\\)".toRegex()
            val find = patternCode.find(it)?.value

            val cccode = countryCodes.find { code.startsWith(it) }
            val ccds = find?.replace("(", "")?.replace(")", "") ?: cccode

            Country3(
                code = ccds?.norm() ?: "".norm(),
                lang = langs.get(ccds)?.norm() ?: "".norm(),
                name = if (find != null) {
                    it.replace(find, "").trim().norm()
                } else {
                    it.trim().norm()
                }
            )
        }

        countries
    }
    .flatMap { it }
    .associateBy { it.code }
    .mapValues { Country(lang = it.value.lang, name = it.value.name) }




fun String.norm() = "\"$this\""

data class CurrencyData(
    val num: Int,
    val scale: Int,
    val name: String,
    val symbol: String,
    val countries: List<String>
)

data class Country3(
    val code: String,
    val lang: String,
    val name: String
)

data class Country(
    val lang: String,
    val name: String
)

fun cur(): String = """
    AED,د.إ.
    AFN,Af
    ALL,L
    AMD,֏
    ANG,ƒ
    AOA,Kz
    ARS,AR${'$'}
    AUD,AU${'$'}
    AWG,ƒ
    AZN,ман
    BAM,KM
    BBD,BBD${'$'}
    BDT,৳
    BGN,лв.
    BHD,BD
    BIF,FBu
    BMD,${'$'}
    BND,B${'$'}
    BOB,Bs.
    BRL,R${'$'}
    BSD,${'$'}
    BTN,Nu.
    BWP,P
    BYN,Br
    BZD,BZ${'$'}
    CAD,CA${'$'}
    CDF,FC
    CHF,Fr.
    CKD,${'$'}
    CLP,CL${'$'}
    CNY,CN¥
    COP,CO${'$'}
    CRC,₡
    CUC,CUC${'$'}
    CUP,${'$'}MN
    CVE,CV${'$'}
    CZK,Kč
    DJF,Fdj
    DKK,kr.
    DOP,RD${'$'}
    DZD,DA
    EGP,E£
    EHP,Ptas.
    ERN,Nkf
    ETB,Br
    EUR,€
    FJD,FJ${'$'}
    FKP,FK£
    FOK,kr
    GBP,£
    GEL,₾
    GGP,£
    GHS,GH₵
    GIP,£
    GMD,D
    GNF,FG
    GTQ,Q
    GYD,G${'$'}
    HKD,HK${'$'}
    HNL,L
    HRK,kn
    HTG,G
    HUF,Ft
    IDR,Rp
    ILS,₪
    IMP,£
    INR,Rs.
    IQD,د.ع.
    IRR,﷼
    ISK,kr
    JEP,£
    JMD,J${'$'}
    JOD,JD
    JPY,¥
    KES,KSh
    KGS,с
    KHR,៛
    KID,${'$'}
    KMF,CF
    KPW,₩
    KRW,₩
    KWD,KD
    KYD,CI${'$'}
    KZT,₸
    LAK,₭N
    LBP,LL.
    LKR,Rs.
    LRD,L${'$'}
    LSL,L
    LYD,LD
    MAD,DH
    MDL,L
    MGA,Ar
    MKD,den
    MMK,Ks
    MNT,₮
    MOP,MOP${'$'}
    MRU,UM
    MUR,Rs.
    MVR,MRf
    MWK,MK
    MXN,MX${'$'}
    MYR,RM
    MZN,MTn
    NAD,N${'$'}
    NGN,₦
    NIO,C${'$'}
    NOK,kr
    NPR,Rs.
    NZD,NZ${'$'}
    OMR,OR
    PAB,B/.
    PEN,S/.
    PGK,K
    PHP,₱
    PKR,Rs.
    PLN,zł
    PND,${'$'}
    PRB,р.
    PYG,₲
    QAR,QR
    RON,L
    RSD,din
    RUB,₽
    RWF,FRw
    SAR,SR
    SBD,SI${'$'}
    SCR,Rs.
    SDG,£SD
    SEK,kr
    SGD,S${'$'}
    SHP,£
    SLL,Le
    SLS,Sl
    SOS,Sh.So.
    SRD,Sr${'$'}
    SSP,SS£
    STN,Db
    SVC,₡
    SYP,LS
    SZL,L
    THB,฿
    TJS,SM
    TMT,m.
    TND,DT
    TOP,T${'$'}
    TRY,TL
    TTD,TT${'$'}
    TVD,${'$'}
    TWD,NT${'$'}
    TZS,TSh
    UAH,₴
    UGX,USh
    USD,${'$'}
    UYU,{'$'}U
    UZS,сум
    VED,Bs.
    VES,Bs.F
    VND,₫
    VUV,VT
    WST,T
    XAF,Fr
    XCD,${'$'}
    XOF,₣
    XPF,₣
    YER,YR
    ZAR,R
    ZMW,ZK
    ZWB,
    ZWL,Z${'$'}
    Abkhazia,
    Artsakh,դր.
""".trimIndent()

fun res(): String = """
    AED||784||2||United Arab Emirates dirham||United Arab Emirates
    AFN||971||2||Afghan afghani||Afghanistan
    ALL||008||2||Albanian lek||Albania
    AMD||051||2||Armenian dram||Armenia
    ANG||532||2||Netherlands Antillean guilder||Curaçao (CW),Sint Maarten (SX)
    AOA||973||2||Angolan kwanza||Angola
    ARS||032||2||Argentine peso||Argentina
    AUD||036||2||Australian dollar||Australia,Christmas Island (CX),Cocos Keeling Islands (CC),Heard Island and McDonald Islands (HM),Kiribati (KI),Nauru (NR),Norfolk Island (NF),Tuvalu (TV)
    AWG||533||2||Aruban florin||Aruba
    AZN||944||2||Azerbaijani manat||Azerbaijan
    BAM||977||2||Bosnia and Herzegovina convertible mark||Bosnia and Herzegovina
    BBD||052||2||Barbados dollar||Barbados
    BDT||050||2||Bangladeshi taka||Bangladesh
    BGN||975||2||Bulgarian lev||Bulgaria
    BHD||048||3||Bahraini dinar||Bahrain
    BIF||108||0||Burundian franc||Burundi
    BMD||060||2||Bermudian dollar||Bermuda
    BND||096||2||Brunei dollar||Brunei
    BOB||068||2||Boliviano||Bolivia
    BRL||986||2||Brazilian real||Brazil
    BSD||044||2||Bahamian dollar||Bahamas
    BTN||064||2||Bhutanese ngultrum||Bhutan (BT)
    BWP||072||2||Botswana pula||Botswana
    BYN||933||2||Belarusian ruble|| Belarus
    BZD||084||2||Belize dollar||Belize
    CAD||124||2||Canadian dollar||Canada
    CDF||976||2||Congolese franc||Democratic Republic of the Congo
    CHF||756||2||Swiss franc||Switzerland,Liechtenstein (LI)
    CLP||152||0||Chilean peso||Chile
    COP||170||2||Colombian peso||Colombia
    CRC||188||2||Costa Rican colon||Costa Rica
    CUC||931||2||Cuban convertible peso||Cuba
    CUP||192||2||Cuban peso||Cuba
    CVE||132||2||Cape Verdean escudo||Cabo Verde
    CZK||203||2||Czech koruna||Czechia
    DJF||262||0||Djiboutian franc||Djibouti
    DKK||208||2||Danish krone||Denmark,Faroe Islands (FO),Greenland (GL)
    DOP||214||2||Dominican peso||Dominican Republic
    DZD||012||2||Algerian dinar||Algeria
    EGP||818||2||Egyptian pound||Egypt
    ERN||232||2||Eritrean nakfa||Eritrea
    ETB||230||2||Ethiopian birr||Ethiopia
    EUR||978||2||Euro||Åland Islands (AX),European Union (EU),Andorra (AD), Austria (AT),Belgium (BE),Cyprus (CY),Estonia (EE),Finland (FI),France (FR),French Southern and Antarctic Lands (TF),Germany (DE),Greece (GR),Guadeloupe (GP),Ireland (IE),Italy (IT),Latvia (LV),Lithuania (LT),Luxembourg (LU),Malta (MT),French Guiana (GF),Martinique (MQ),Mayotte (YT),Monaco (MC),Montenegro (ME),Netherlands (NL),Portugal (PT),Réunion (RE),Saint Barthélemy (BL),Saint Martin (MF),Saint Pierre and Miquelon (PM),San Marino (SM),Slovakia (SK),Slovenia (SI),Spain (ES),Vatican City (VA)
    FJD||242||2||Fijian dollar|Fiji dollar||Fiji
    FKP||238||2||Falkland Islands pound||Falkland Islands
    GBP||826||2||Pound sterling||United Kingdom,Isle of Man (IM),Jersey (JE),Guernsey (GG)
    GEL||981||2||Georgian lari||Georgia
    GHS||936||2||Ghanaian cedi||Ghana
    GIP||292||2||Gibraltar pound||Gibraltar
    GMD||270||2||Gambian dalasi||Gambia
    GNF||324||0||Guinean franc||Guinea
    GTQ||320||2||Guatemalan quetzal||Guatemala
    GYD||328||2||Guyanese dollar||Guyana
    HKD||344||2||Hong Kong dollar||Hong Kong
    HNL||340||2||Honduran lempira||Honduras
    HRK||191||2||Croatian kuna||Croatia
    HTG||332||2||Haitian gourde||Haiti
    HUF||348||2||Hungarian forint||Hungary
    IDR||360||2||Indonesian rupiah||Indonesia
    ILS||376||2||Israeli new shekel||Israel
    INR||356||2||Indian rupee||India,Bhutan (BT)
    IQD||368||3||Iraqi dinar||Iraq
    IRR||364||2||Iranian rial||Iran
    ISK||352||0||Icelandic króna||Iceland
    JMD||388||2||Jamaican dollar||Jamaica
    JOD||400||3||Jordanian dinar||Jordan
    JPY||392||0||Japanese yen||Japan
    KES||404||2||Kenyan shilling||Kenya
    KGS||417||2||Kyrgyzstani som||Kyrgyzstan
    KHR||116||2||Cambodian riel||Cambodia
    KMF||174||0||Comoro franc||Comoros
    KPW||408||2||North Korean won||North Korea
    KRW||410||0||South Korean won||South Korea
    KWD||414||3||Kuwaiti dinar||Kuwait
    KYD||136||2||Cayman Islands dollar||Cayman Islands
    KZT||398||2||Kazakhstani tenge||Kazakhstan
    LAK||418||2||Lao kip||Laos
    LBP||422||2||Lebanese pound||Lebanon
    LKR||144||2||Sri Lankan rupee||Sri Lanka
    LRD||430||2||Liberian dollar||Liberia
    LSL||426||2||Lesotho loti||Lesotho (LS)
    LYD||434||3||Libyan dinar||Libya
    MAD||504||2||Moroccan dirham||Morocco,Western Sahara (EH)
    MDL||498||2||Moldovan leu||Moldova
    MGA||969||2||Malagasy ariary||Madagascar
    MKD||807||2||Macedonian denar||North Macedonia
    MMK||104||2||Myanmar kyat||Myanmar
    MNT||496||2||Mongolian tögrög||Mongolia
    MOP||446||2||Macanese pataca||Macau
    MRU||929||2||Mauritanian ouguiya||Mauritania
    MUR||480||2||Mauritian rupee||Mauritius
    MVR||462||2||Maldivian rufiyaa||Maldives (MV)
    MWK||454||2||Malawian kwacha||Malawi
    MXN||484||2||Mexican peso||Mexico
    MYR||458||2||Malaysian ringgit||Malaysia
    MZN||943||2||Mozambican metical||Mozambique
    NAD||516||2||Namibian dollar||Namibia (NA)
    NGN||566||2||Nigerian naira||Nigeria
    NIO||558||2||Nicaraguan córdoba||Nicaragua
    NOK||578||2||Norwegian krone||Norway,Svalbard and Jan Mayen (SJ),Bouvet Island (BV)
    NPR||524||2||Nepalese rupee||Nepal
    NZD||554||2||New Zealand dollar||New Zealand,Cook Islands (CK),Niue (NU),Pitcairn Islands (PN),Tokelau (TK)
    OMR||512||3||Omani rial||Oman
    PAB||590||2||Panamanian balboa||Panama
    PEN||604||2||Peruvian sol||Peru
    PGK||598||2||Papua New Guinean kina||Papua New Guinea
    PHP||608||2||Philippine peso||Philippines
    PKR||586||2||Pakistani rupee||Pakistan
    PLN||985||2||Polish złoty||Poland
    PYG||600||0||Paraguayan guaraní||Paraguay
    QAR||634||2||Qatari riyal||Qatar
    RON||946||2||Romanian leu||Romania
    RSD||941||2||Serbian dinar||Serbia
    CNY||156||2||Renminbi|| China
    RUB||643||2||Russian ruble||Russia
    RWF||646||0||Rwandan franc||Rwanda
    SAR||682||2||Saudi riyal||Saudi Arabia
    SBD||090||2||Solomon Islands dollar||Solomon Islands
    SCR||690||2||Seychelles rupee||Seychelles
    SDG||938||2||Sudanese pound||Sudan
    SEK||752||2||Swedish krona||Sweden
    SGD||702||2||Singapore dollar||Singapore
    SHP||654||2||Saint Helena pound||Saint Helena (SH)
    SLL||694||2||Sierra Leonean leone||Sierra Leone
    SOS||706||2||Somali shilling||Somalia
    SRD||968||2||Surinamese dollar||Suriname
    SSP||728||2||South Sudanese pound||South Sudan
    STN||930||2||São Tomé and Príncipe dobra||São Tomé and Príncipe
    SVC||222||2||Salvadoran colón||El Salvador
    SYP||760||2||Syrian pound||Syria
    SZL||748||2||Swazi lilangeni||Eswatini (SZ)
    THB||764||2||Thai baht||Thailand
    TJS||972||2||Tajikistani somoni||Tajikistan
    TMT||934||2||Turkmenistan manat||Turkmenistan
    TND||788||3||Tunisian dinar||Tunisia
    TOP||776||2||Tongan paʻanga||Tonga
    TRY||949||2||Turkish lira||Turkey
    TTD||780||2||Trinidad and Tobago dollar||Trinidad and Tobago
    TWD||901||2||New Taiwan dollar||Taiwan
    TZS||834||2||Tanzanian shilling||Tanzania
    UAH||980||2||Ukrainian hryvnia||Ukraine
    UGX||800||0||Ugandan shilling||Uganda
    USD||840||2||United States dollar||United States,American Samoa (AS),British Indian Ocean Territory (IO),British Virgin Islands (VG),Caribbean Netherlands (BQ),Ecuador (EC),El Salvador (SV),Guam (GU),Marshall Islands (MH),Federated States of Micronesia (FM),Northern Mariana Islands (MP),Palau (PW),Panama (PA),Puerto Rico (PR),Timor-Leste (TL),Turks and Caicos Islands (TC),U.S. Virgin Islands (VI),United States Minor Outlying Islands (UM)
    UYU||858||2||Uruguayan peso||Uruguay
    UZS||860||2||Uzbekistan som||Uzbekistan
    VED||926||2||Venezuelan bolívar|Venezuelan bolívar digital||Venezuela
    VES||928||2||Venezuelan bolívar|Venezuelan bolívar soberano||Venezuela
    VND||704||0||Vietnamese đồng||Vietnam
    VUV||548||0||Vanuatu vatu||Vanuatu
    WST||882||2||Samoan tala||Samoa
    XAF||950||0||Central African CFA franc|CFA franc BEAC||Cameroon (CM),Central African Republic (CF),Republic of the Congo (CG),Chad (TD),Equatorial Guinea (GQ),Gabon (GA)
    XCD||951||2||East Caribbean dollar||Anguilla (AI),Antigua and Barbuda (AG),Dominica (DM),Grenada (GD),Montserrat (MS),Saint Kitts and Nevis (KN),Saint Lucia (LC),Saint Vincent and the Grenadines (VC)
    XOF||952||0||West African CFA franc|CFA franc BCEAO||Benin (BJ),Burkina Faso (BF),Côte d'Ivoire (CI),Guinea-Bissau (GW),Mali (ML),Niger (NE),Senegal (SN),Togo (TG)
    XPF||953||0||CFP franc ||French Polynesia (PF),New Caledonia (NC),Wallis and Futuna (WF)
    YER||886||2||Yemeni rial||Yemen
    ZAR||710||2||South African rand||Eswatini (SZ),Lesotho,Namibia (NA),South Africa
    ZMW||967||2||Zambian kwacha||Zambia
    ZWL||932||2||Zimbabwean dollar||Zimbabwe
""".trimIndent()

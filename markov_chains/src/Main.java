import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class Main {

    private static final String START_PART = "[->";
    private static final String END_PART = "->]";

    public static void main(String[] args) {
        String text = "Technologijų antreprenerystės modulį pasirinkau todėl, nes norėjau įgyti pagrindinių žinių, kaip reikia tapti jauno verslo kūrėju turint technologinę idėją. Taip pat pasirinkti šį modulį mane paskatino tai, kad šis modulis turi savo „Facebook“ paskyrą, kurioje yra dalinamasi įvairiais, modulio tematiką atitinkančiais, renginiais, modulio metu suburtų komandų sėkmės istorijomis bei kita modulio veikla. Tai studentams leidžia lengviau suprasti, apie ką yra šis modulis ir kokios yra jo perspektyvos. Modulio metu yra ugdomi baziniai gebėjimai, kaip reikia atpažinti, plėtoti ir komunikuoti technologinę idėją, kaip ją komercializuoti, pritraukti finansinius ir žmogiškuosius išteklius bei valdyti spartaus įmonės augimo procesą. Gaila, bet šio modulio paskaitas nelankiau taip aktyviai, kaip buvau planavęs. Vis dėlto, esu labai patenkintas, kad modulio dėstytojai suteikia didelį grįžtamąjį ryšį studentams. „Moodle“ sistemoje yra pateikta visa modulio medžiaga, studentai gauna informacinius laiškus elektroniniu paštu apie artėjančius atsiskaitymus ir būsimus paskaitų svečius. Dėl šios priežasties galėjau dalį teorijos mokytis savarankiškai. Aš, kaip antrepreneriškai veikiantis individas, modulio metu įgavau žinių, kaip galėčiau būti nuolat tobulėjanti asmenybė ir aiškiai matyti savo viziją. Tokiais apibūdinimais yra aprašoma lyderio sąvoka. Pagrindinis skirtumas tarp verslininko/vadovo ir lyderio yra tas, kad jis įkvepia žmones ir juda kartu su jais kryptingai, pasikliauja savo valia, yra drąsūs ir atjaučiantys. Tokios pateiktos antrepreneriško individo gaires man yra vertingos, nes tai galima panaudoti siekiant suburti efektyviai dirbančią komandą bei įgyvendinti savo technologinę idėją. Taip pat man patiko modulio metu pateikti aktyvaus klausymosi principai, kurie ugdo mano, kaip antrepreneriškai veikiančio individo, komunikacinius sugebėjimus. Aš, kaip komandos narys, semestro eigoje patobulėju komandinio darbo veikloje, įgavau svarbios patirties, išmokau, kaip reikia dirbti didelėje komandoje. Komandos semestro buvo skatinamos spręsti globalias problemas, todėl ir mes stengėmės sugalvoti ir vėliau realizuoti inovatyvią technologinę idėja. Komandoje pasiskirstėme darbais, kiekvienas stengėsi prisidėti ir atlikti savo darbą kūrybiškai. Šiam tikslui įgyvendinti labai padėjo paskaitų metu pristatytas De Bono kūrybiško mąstymo metodas. Kiekvienas komandos narys turėjo savo atsakomybes ir spręstinus uždavinius, kurie parodė nario asmenybės stipriąsias puses. Aš, kaip jaunojo verslo / idėjos vystytojas, susipažinau su verslo modelio drobe ir sužinojau, kas yra judrus startuolis. Semestro metu pristatytas lankstusis produkto kūrimas leido komandinio projekto metu apgalvoti ir tinkamai susidaryti idėjos verslo drobę. Mano manymu, verslo modelio drobė mūsų komandai padėjo susidaryti idėjos realizacijos planą bei nustatyti svarbiausius faktorius, iškelti sau klausimus bei surasti atsakymus, kokią naudą sukuria mūsų kuriamas produktas, kokių investicijų tai reikalauja ir t.t. Taip pat modulio metu man buvo naudinga informacija apie projekto finansus. Tai leido nustatyti mūsų idėjos realizacijos išlaidas ir tikėtinas pajamas.  Vis dėlto, pati svarbiausia patirtis, mano manymu, buvo viešasis kalbėjimas. Tai yra vienas svarbiausių dalykų siekiant gerai iškomunikuoti savo produktą ir pritraukti investuotojų. Taip pat tarpinių pristatymų metu dėstytojų pateiktos pastabos padėjo komandai išgryninti savo idėją bei identifikuoti ir pataisyti padarytas klaidas.";

        List<String> sentenceParts = getPartsFromComment(text);
        addFirstAndLastPartToList(sentenceParts);
        Map<String, ChildWords> wordPool = getWordSequenceMap(sentenceParts.toArray(new String[0]));

        System.out.println("-".repeat(100));
        System.out.println(generateRandomText(wordPool));
        System.out.println("-".repeat(100));
        System.out.println(generateRandomText(wordPool));
        System.out.println("-".repeat(100));
        System.out.println(generateRandomText(wordPool));
        System.out.println("-".repeat(100));
    }

    private static List<String> getPartsFromComment(String comment) {
        return Pattern.compile("[a-zA-ZąĄčČęĘėĖįĮšŠųŲūŪžŽ]+|[^\\w\\s]|")
                .matcher(comment)
                .results()
                .map(MatchResult::group)
                .collect(Collectors.toList());
    }

    private static void addFirstAndLastPartToList(List<String> sentenceParts) {
        sentenceParts.add(0, START_PART);
        sentenceParts.add(END_PART);
    }

    private static Map<String, ChildWords> getWordSequenceMap(String[] sentenceParts) {
        Map<String, ChildWords> wordPoll = new HashMap<>();

        for (int i = 0; i < sentenceParts.length - 1; i++) {
            String parentWord = sentenceParts[i];
            String childWord = sentenceParts[i+1];

            ChildWords childWords = wordPoll.get(parentWord);

            if (childWords != null) {
                childWords.mergeWord(childWord);
            } else {
                wordPoll.put(parentWord, new ChildWords(childWord));
            }
        }

        return wordPoll;
    }

    private static String generateRandomText(Map<String, ChildWords> wordPool) {
        Random random = new Random();
        StringBuilder text = new StringBuilder();
        String parentWord = wordPool.get(START_PART).getRandomWord(random);

        while (!parentWord.equals(END_PART)) {
            text.append(parentWord);
            text.append(" ");
            parentWord = wordPool.get(parentWord).getRandomWord(random);
        }

        return text.toString();
    }
}
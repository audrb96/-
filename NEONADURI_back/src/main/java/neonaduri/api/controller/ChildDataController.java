package neonaduri.api.controller;

import lombok.RequiredArgsConstructor;
import neonaduri.api.repository.ClassificationRepository;
import neonaduri.api.repository.RegionRepository;
import neonaduri.api.repository.SpotRepository;
import neonaduri.domain.Classification;
import neonaduri.domain.Region;
import neonaduri.domain.Spot;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.print.Doc;
import java.io.*;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ChildDataController {

    private final RegionRepository regionRepository;

    private final ClassificationRepository classificationRepository;

    private final SpotRepository spotRepository;

    /**
     * child data 주입
     */
    @GetMapping("/child")
    @Transactional
    public void childFacilityData() {

        BufferedReader br = null;

        try {
            //파일 넣어준다.
            br = new BufferedReader(new InputStreamReader(new FileInputStream("C:\\Users\\gus48\\Desktop\\702\\S07P22A702\\data\\childFacility.csv"), "UTF-8"));
            String line = "";
            br.readLine(); //첫 행은 버린다. (컬럼명이므로)

            //파일이 끝날 때까지 돌린다.
            while ((line = br.readLine()) != null) {
                /*
                  *을 기준으로 찢어서 배열에 담아준다.
                  array[0] : 시설 명
                  array[1] : 주소
                  array[2] : 전화번호
                  array[3] : 위도
                  array[4] : 경도
                 */
                String[] array = line.split("\\*");

/** regionId 뽑기 */
//                주소를 띄어쓰기 기준으로 쪼갠다.
//                2번 인덱스가 시/군/구로 끝난다면 시군구가 두 단어, 3번 인덱스가 면
//                그렇지 않다면 2번 인덱스가 면
                String[] address = array[1].split(" ");

                String sido = null;
                String[] sidoList = {"서울특별시", "세종특별자치시", "제주특별자치도", "경기도", "강원도", "부산광역시", "대구광역시", "인천광역시", "광주광역시", "대전광역시", "울산광역시", "충청북도", "충청남도", "경상북도", "경상남도", "전라북도", "전라남도"};

                //시도명 올바르게 넣어줌
                if (address[0].equals("서울")) {
                    sido = "서울특별시";
                } else if (address[0].equals("부산") || address[0].equals("대구") || address[0].equals("인천") || address[0].equals("광주") || address[0].equals("대전") || address[0].equals("울산")) {
                    sido = address[0] + "광역시";
                }
//                else if (address[0].equals("세종특별자치시") || address[0].equals("제주특별자치도") || address[0].equals("경기도") || address[0].equals("강원도")
//                        || address[0].equals("부산광역시") || address[0].equals("대구광역시") || address[0].equals("인천광역시") || address[0].equals("광주광역시")
//                        || address[0].equals("대전광역시") || address[0].equals("울산광역시") || address[0].equals("충청북도") || address[0].equals("충청남도")
//                        || address[0].equals("경상북도") || address[0].equals("경상남도") || address[0].equals("전라북도") || address[0].equals("전라남도")) {
//                    sido = address[0];
//                }
                else if (Arrays.asList(sidoList).contains(address[0])) {
                    sido = address[0];
                } else if (address[0].equals("경기") || address[0].equals("강원")) {
                    sido = address[0] + "도";
                } else if (address[0].equals("충북")) {
                    sido = "충청북도";
                } else if (address[0].equals("충남")) {
                    sido = "충청남도";
                } else if (address[0].equals("경북")) {
                    sido = "경상북도";
                } else if (address[0].equals("경남")) {
                    sido = "경상남도";
                } else if (address[0].equals("전북")) {
                    sido = "전라북도";
                } else if (address[0].equals("전남")) {
                    sido = "전라남도";
                }


                String sigungu = null;
                String myeon = null;

                String check = address[2]; //사용하기 쉽게 check 이라는 변수에 담아준다.

                //세종특별자치시일 경우 (데이터에 시군구 내용 없음)
                if (address[0].charAt(0) == '세') {
                    sigungu = "세종시";
                    myeon = address[1];
                }
                //세종시가 아닌 경우
                else {
                    //check가 시군구라면 check 버립니다.
                    if (check.charAt(check.length() - 1) == '시' || check.charAt(check.length() - 1) == '군' || check.charAt(check.length() - 1) == '구') {
                        sigungu = address[1];
                        myeon = address[3];
                    }
                    //check가 시군구가 아니라면 check가 면
                    else {
                        sigungu = address[1];
                        myeon = address[2];
                    }
                }

                //용담1동, 용담2동, 용담3동, 일도일동, 일도이동
                if (myeon.contains("용담")) {
                    if (myeon.contains("1")) {
                        myeon = "용담일동";
                    } else if (myeon.contains("2")) {
                        myeon = "용담이동";
                    } else if (myeon.contains("3")) {
                        myeon = "용담삼동";
                    }
                } else if (myeon.contains("일도")) {
                    if (myeon.contains("1")) {
                        myeon = "일도일동";
                    } else if (myeon.contains("2")) {
                        myeon = "일도이동";
                    }
                }

                //경상북도 구미시 산동면 -> 산동읍
                if (myeon.equals("산동면") && sigungu.equals("구미시")) {
                    myeon = "산동읍";
                }

                //부산광역시 기장군 일광읍 -> 일광면
                if (myeon.equals("일광읍") && sigungu.equals("기장군")) {
                    myeon = "일광면";
                }

                //경상북도 용인시 남사면 -> 남사읍
                if (myeon.equals("남사면") && sigungu.equals("용인시")) {
                    myeon = "남사읍";
                }


                //논현동
                if (myeon.contains("논현") && sigungu.equals("강남구")) {
                    myeon = "논현동";
                }

                //목동
                if (sigungu.equals("양천구") && myeon.contains("목")) {
                    myeon = "목동";
                }


                //범일동
                if (myeon.contains("범일") && sigungu.equals("동구")) {
                    myeon = "범일동";
                }


                //만수동
                if (myeon.contains("만수") && sido.equals("인천광역시")) {
                    myeon = "만수동";
                }

                //홍천군 동면 -> 영귀미면
                if (myeon.equals("동면") && sigungu.contains("홍천")) {
                    myeon = "영귀미면";
                }

                //양구군 남면 -> 국토정중앙면
                if (myeon.equals("남면") && sigungu.equals("양구군")) {
                    myeon = "국토정중앙면";
                }

                //영월군 중동면 -> 산솔면
                if (myeon.equals("중동면") && sigungu.equals("영월군")) {
                    myeon = "산솔면";
                }

                //경주시 양북면 -> 문무대왕면
                if (myeon.equals("양북면") && sigungu.equals("경주시")) {
                    myeon = "문무대왕면";
                }


                //추출한 시군구, 면으로 지역코드 뽑아낸다.
                Region region = regionRepository.findRegionBySidoAndSigunguAndMyeon(sido, sigungu, myeon);
                Long regionId = region.getRegionId();


/** classification 뽑기*/
                //spotName 이 지정된 캐릭터명을 포함하는지 확인하여 분류
                //뽀로로, 타요, 폴리, 라바, 기타
                Classification cls;

                if (array[0].contains("뽀로로")) {
                    cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "뽀로로");
                } else if (array[0].contains("타요")) {
                    cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "타요");
                } else if (array[0].contains("롤리폴리")) {
                    cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "기타");
                } else if (array[0].contains("아시아폴리스")) {
                    cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "기타");
                } else if (array[0].contains("폴리")) {
                    cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "폴리");
                } else if (array[0].contains("라바")) {
                    cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "라바");
                } else {
                    cls = classificationRepository.findClassificationByMdClassAndSmClass("어린이", "기타");
                }

                Long clsId = cls.getClassId();

/** img 크롤링 */
                String prefixURl = "https://www.google.com/search?q=";
                String suffixURl = "&tbm=isch";
//                if (spot == null) {
                String encodeUrl = URLEncoder.encode(array[0], "UTF-8");
                String url = prefixURl + encodeUrl + suffixURl;
//                    url = URLEncoder.encode(url, "UTF-8");
                Connection cn = Jsoup.connect(url);
                Document doc = cn.get();
//                Elements select = document.select("div.bRMDJf.islir");
//                Element imgElement = (Element) select
//                System.out.println(imgElement.attr("data-src"));
                String img = doc.getElementsByClass("rg_i Q4LuWd").attr("data-src");

//                System.out.println(img);
//                    img = URLDecoder.decode(doc.select("img.rg_i.Q4LuWd").attr("data-src"), "UTF-8");
//                                        String img = doc.select("img.rg_i.Q4LuWd").attr("data-src");


                Spot spot = new Spot(
                        clsId,
                        Float.parseFloat(array[3]),
                        Float.parseFloat(array[4]),
                        regionId,
                        img,
                        array[0],
                        array[2]

                );
                spotRepository.save(spot);
            }


//            }


        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        //스트림 닫아준다.
        finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}

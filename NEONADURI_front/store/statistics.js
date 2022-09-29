import {getSat,getVisited,getSel} from '~/api/statistics'
import koreaMap from '~/static/map/koreaMap.json'

export const state = () =>(
    {
        words:[],
        satList:[],
        regionList:[],
        koreaMap,
        introData:{},
    }
)


export const mutations = {
    SET_SAT(state, payload){
        state.satList=payload
    },
    SET_SEL(state, payload){
      state.words=[]
      payload.forEach(element=>{
        const arr=[]

        arr.push(element.selReason);
        arr.push(element.selPercent);

        state.words.push(arr);
      })
    },
    SET_VISITED(state, payload){
      state.regionList=[]

      state.introData.maxValue=0;
      state.introData.minValue=payload[0].visitedNum

      payload.forEach(element=>{
        const obj={}

        obj.name=element.visitedRegion;
        obj.value=element.visitedNum;

        state.regionList.push(obj);
        // ///////////////////////
        if(state.introData.maxValue < obj.value){
            state.introData.maxValue=obj.value
        }

        if(state.introData.minValue > obj.value){
            state.introData.minValue=obj.value
        }
      })
    }
}

export const getters ={

}


export const actions = {
    callSatList({commit}){
        getSat(
            ({data})=>{
                commit('SET_SAT',data)
            },
            (error)=>{
                console.log(error)
            }
        )
    },
    callSelList({commit}){
        getSel(
            ({data})=>{
                commit('SET_SEL',data)
            },
            (error)=>{
                console.log(error)
            }
        )
    },
    async callVisitedList({commit}){
        await getVisited(
            ({data})=>{
                commit('SET_VISITED',data)
            },
            (error)=>{
                console.log(error)
            }
        )
    }
}

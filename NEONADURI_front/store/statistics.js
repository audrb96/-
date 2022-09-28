import {getSat,getVisited,getSel} from '~/api/statistics'

export const state = () =>(
    {
        words:[],
        // priceList:[],
        // foodList:[],
        // natureList:[],
        satList:[],
        regionList:[],
    }
)


export const mutations = {
    SET_SAT(state, payload){
        state.satList=payload
    },
    SET_SEL(state, payload){
      payload.forEach(element=>{
        const arr=[]

        arr.push(element.selReason);
        arr.push(element.selPercent);

        state.words.push(arr);
      })
    },
    SET_VISITED(state, payload){
      // state.words=payload
      state.regionList=payload
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
    callVisitedList({commit}){
        getVisited(
            ({data})=>{
                commit('SET_VISITED',data)
            },
            (error)=>{
                console.log(error)
            }
        )
    }
}

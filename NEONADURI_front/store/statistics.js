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
        console.log("mutataion : "+payload)
        // payload.forEach(element => {
        //   console.log(element.satType)

        //   if(element.satType ==='0'){
        //     state.priceList.push(payload.satScore);
        //   }else if(element.satType==='1'){
        //     state.foodList.push(payload.satScore);
        //   }if(element.satType==='2'){
        //     state.natureList.push(payload.satScore);
        //   }
        // });
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
      payload.forEach(element=>{
        const arr=[];

        arr.push(element.visitedRegion);
        arr.push(element.visitedNum);

        state.regionList.push(arr);
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

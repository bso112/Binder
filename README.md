<h1 align="center">Binder</h1>

<p align="center">
<img src="https://img.shields.io/badge/-Android-FA7343?style=flat&logo=Android"/>
<img alt="API" src="https://img.shields.io/badge/API-21%2B-brightgreen.svg?style=flat"/>
</p>

Binder is a utility library that simplifies the process of creating Adapters and ViewHolders for RecyclerView in Android. It aims to reduce boilerplate code and improve productivity.

## What is Binder?
**Binder generalizes RecyclerView.Adapter & ViewHolder in a very simple way.**
You no longer need to create multiple Adapters or ViewHolders.

## Setup
To get a Git project into your build:

**Step 1. Add the JitPack repository to your build file**

Add it in your root build.gradle at the end of repositories:

```groovy
	allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
 
**Step 2. Add the dependency**
```groovy
	dependencies {
	        implementation 'com.github.bso112:Binder:master-SNAPSHOT'
	}
```
Replace 'master-SNAPSHOT' with the version number if you're using a specific version.
## How to use
### Create UIModel
```kotlin
sealed class SectionUIModel : IdentifiableUIModel() {
    abstract val sectionTitle: String
    abstract val productList: List<ProductUIModel>

    var onClickSection: ((SectionUIModel) -> Unit)? = null

    data class Horizontal(
        override val id: Id,
        override val sectionTitle: String,
        override val productList: List<ProductUIModel>
    ) : SectionUIModel() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_horizontal
    }

    data class Vertical(
        override val id: Id,
        override val sectionTitle: String,
        override val productList: List<ProductUIModel>
    ) : SectionUIModel() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_vertical
    }

    data class Grid(
        override val id: Id,
        override val sectionTitle: String,
        override val productList: List<ProductUIModel>
    ) : SectionUIModel() {
        override val bindingVariableId: Int = BR.item
        override val layoutId: Int = R.layout.item_section_grid
    }
```
Create a UIModel class that is derived from either IdentifiableUIModel or BindableUIModel. 
IdentifiableUIModel is an abstract class that is derived from BindableUIModel.
IdentifiableUIModel comes in handy when you want to implement the areItemsTheSame(), and areContentsTheSame() methods in a default way.
</br>
### Bind UIModel to View
```kotlin
        binding.rvSection.adapter = BindingPagingDataAdapter<SectionUIModel>(
            buildBinder<ItemSectionHorizontalBinding, SectionUIModel.Horizontal> { item ->
                item.onClickSection = {
                    viewModel.deleteSection(it)
                }
            },
            buildBinder<ItemSectionVerticalBinding, SectionUIModel.Vertical> { item ->
                item.onClickSection = {
                    viewModel.deleteSection(it)
                }
            },
            buildBinder<ItemSectionGridBinding, SectionUIModel.Grid> { item ->
                item.onClickSection = {
                    viewModel.deleteSection(it)
                }
            }
        )
    }
}
```
Create a BindingPagingDataAdapter or a BindingListAdapter depending on your needs. 
When creating these, you need to pass a BinderBuilder as a parameter. 
The buildBinder is a helper method used for creating a BinderBuilder. 
A Binder is similar to a ViewHolder as it holds the data and logic necessary to bind a UIModel to a View.


